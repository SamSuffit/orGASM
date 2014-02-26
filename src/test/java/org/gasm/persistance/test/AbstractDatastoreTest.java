package org.gasm.persistance.test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.devrel.samples.listener.InitContextListener;
import org.gasm.matos.dao.AdherentDao;
import org.gasm.matos.dao.DivingEventDao;
import org.gasm.matos.dao.JacketDao;
import org.gasm.matos.dao.RentHistoryDao;
import org.gasm.matos.entity.Adherent;
import org.gasm.matos.entity.Jacket;
import org.gasm.matos.entity.enums.Brand;
import org.gasm.matos.entity.enums.Size;
import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.matos.entity.rental.RentalRecord;
import org.gasm.matos.entity.rental.billing.BillingType;
import org.gasm.matos.rest.exception.ItemNotFoundException;
import org.gasm.matos.rest.json.RentalRecordAPI;
import org.junit.After;
import org.junit.Before;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 24/02/14
 * Time: 10:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDatastoreTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private final InitContextListener initContextListener = new InitContextListener();

    protected AdherentDao adherentDao;
    protected JacketDao jacketDao;
    protected DivingEventDao divingEventDao;
    protected RentHistoryDao rentHistoryDao;


    protected RentalRecordAPI rentalRecordAPI;



    @Before
    public void setUpAbstract() {
        helper.setUp();

        //Init ofy entities
        initContextListener.contextInitialized(null);

        adherentDao = new AdherentDao();
        jacketDao = new JacketDao();
        divingEventDao = new DivingEventDao();
        rentalRecordAPI = new RentalRecordAPI();
        rentHistoryDao = new RentHistoryDao();
    }

    @After
    public void tearDownAbstract() {
        helper.tearDown();

        //Destroy of entities if needed
        //Init ofy entities
        initContextListener.contextDestroyed(null);
    }

    protected Adherent createAdherent(String firstName, String lastName) {
        Adherent adherent = new Adherent();
        adherent.setFirstName(firstName);
        adherent.setLastName(lastName);
        adherentDao.createOrUpdate(adherent);
        return adherent;
    }

    protected Jacket createJacket(String reference, Size size, Brand brand) {
        Jacket jacket = new Jacket();
        jacket.setReference(reference);
        jacket.setSize(size);
        jacket.setBrand(brand);
        jacketDao.createOrUpdate(jacket);
        return jacket;
    }

    protected DivingEvent createDivingEvent(Adherent inCharge) {
        DivingEvent dEvent = new DivingEvent();
        dEvent.setBillingType(BillingType.Standard);
        dEvent.setDate(new Date());
        dEvent.setInCharge(inCharge);
        dEvent.setPlace("SomeWhere");
        divingEventDao.createOrUpdate(dEvent);
        return dEvent;
    }
    protected RentalRecord createRentalRecord(DivingEvent divingEvent, Adherent renter) throws ItemNotFoundException {
        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setDEvent(divingEvent);
        rentalRecord.setPaid(false);
        rentalRecord.setRenter(renter);
        rentalRecordAPI.createOrUpdate(rentalRecord);
        return rentalRecord;
    }
}