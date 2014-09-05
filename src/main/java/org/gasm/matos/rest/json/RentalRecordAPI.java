package org.gasm.matos.rest.json;

import org.apache.commons.lang3.StringUtils;
import org.gasm.concurrency.KeyMutex;
import org.gasm.matos.dao.AdherentDao;
import org.gasm.matos.dao.DivingEventDao;
import org.gasm.matos.dao.EquipmentDao;
import org.gasm.matos.dao.RentalRecordDao;
import org.gasm.matos.entity.Equipment;
import org.gasm.matos.entity.exception.IllegalRentStatusException;
import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.matos.entity.rental.RentalRecord;
import org.gasm.matos.entity.rental.entity.Payment;
import org.gasm.matos.rest.exception.EquipmentNotFoundException;
import org.gasm.matos.rest.exception.ItemNotFoundException;
import org.gasm.persistance.dao.AbstractLongIdDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

import static org.gasm.concurrency.KeyMutex.*;

@Path("/rentalRecord")
public class RentalRecordAPI extends AbstractLongIdAPI<RentalRecord> {

    private final Logger log = Logger.getLogger(getClass().getName());

    private RentalRecordDao dao;

    private DivingEventDao divingEventDao;
    private EquipmentDao equipmentDao;
    private AdherentDao adherentDao;

    public RentalRecordAPI() {
        super();
        dao = new RentalRecordDao();
        equipmentDao = new EquipmentDao();
        divingEventDao = new DivingEventDao();
        adherentDao = new AdherentDao();
    }

    @Override
	public AbstractLongIdDao<RentalRecord> getDao() {
		return dao;
	}

    @PUT
    @Path("paid/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public void paid(
            @PathParam("id") Long id
            , @QueryParam("payment") final Payment payment
    ) throws ItemNotFoundException {
        if(payment == null) {
            throw new ItemNotFoundException("You need to specify a payment type");
        }
        if(id == null) {
            throw new ItemNotFoundException("You need to specify a rentalRecord id");
        }
        RentalRecord record = get(id);
        if(record == null) {
            throw new ItemNotFoundException(id,RentalRecord.class);
        }
        record.setPaid(true);
        record.setPayment(payment);
        createOrUpdate(record);
    }

    @PUT
    @Path("addToDivingEvent")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public RentalRecord addToDivingEvent(@QueryParam("dEventId") Long dEventId
            , @QueryParam("renterId") final Long  renterId
            , @QueryParam("equipmentId") final String equipmentId
    ) throws ItemNotFoundException, IllegalRentStatusException, InterruptedException {

        // Pas plusieurs ordres de loc en même temps sur la même sortie...
        final String key = Long.toString(dEventId);
        KeyMutex.Mutex mutex = getMutex(key);
        acquireMutex(mutex);

        log.info("addToDivingEvent dEventId:" + dEventId + " renterId:" + renterId + " equipmentId:" + equipmentId);

        if(renterId == null) {
            throw new ItemNotFoundException("No renterId specified");
        }
        if(dEventId == null) {
            throw new ItemNotFoundException("No dEventId specified");
        }
        if(equipmentId == null) {
            throw new ItemNotFoundException("No equipmentId specified");
        }

        final DivingEvent dEvent = loadDivingEvent(dEventId);
        final RentalRecord record = dEvent.getRentalRecord(renterId);
        addEquipment(record, equipmentId);
        saveRentalRecord(record, dEvent);

        releaseMutex(mutex);

        return record;
    }

    private DivingEvent loadDivingEvent(Long dEventId) throws ItemNotFoundException {
        if(dEventId == null) {
            throw new ItemNotFoundException("No diving event Id specified");
        }
        final DivingEvent dEvent = divingEventDao.get(dEventId);
        if (dEvent == null) {
            throw new ItemNotFoundException("No diving event with Id [" + dEventId + "]");
        }
        return dEvent;
    }

    private void saveRentalRecord(final RentalRecord record, final DivingEvent dEvent) throws ItemNotFoundException, IllegalRentStatusException {

        dao.createOrUpdate(record);
        dEvent.addRentalRecord(record);
        divingEventDao.createOrUpdate(dEvent);

        for(Equipment equipment: record.getEquipments()) {
            equipment.rent(record);
            equipmentDao.createOrUpdate(equipment);
        }
    }

    private void addEquipment(RentalRecord record, String equipmentId) throws EquipmentNotFoundException {
        addEquipment(record, equipmentId,null);
    }

    private <E extends Equipment> void addEquipment(RentalRecord record, String equipmentId,Class<E> clazz) throws EquipmentNotFoundException {
        final Equipment equipment;
        if (StringUtils.isNotBlank(equipmentId)) {
            equipment = equipmentDao.get(equipmentId);
            if (equipment == null) {
                throw new EquipmentNotFoundException(equipmentId, clazz);
            }
            record.addEquipment(equipment);
        }  else { equipment=null; }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public void delete(@PathParam("id") Long id) {
        releaseEquipments(id);
        getDao().delete(id);
    }

    private void releaseEquipments(Long id) {
        RentalRecord record = getDao().get(id);
        for(Equipment equipment : record.getEquipments()) {
            equipment.turnIn();
            equipment.save();
        }
        DivingEvent dEvent = record.getDEvent();
        dEvent.removeRentalRecord(record);
        dEvent.save();
    }
}
