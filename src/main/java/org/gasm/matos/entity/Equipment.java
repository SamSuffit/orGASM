package org.gasm.matos.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.gasm.matos.dao.RentHistoryDao;
import org.gasm.matos.entity.enums.Brand;
import org.gasm.matos.entity.exception.IllegalRentStatusException;
import org.gasm.matos.entity.helper.ObjectifyHelper;
import org.gasm.matos.entity.rental.RentalRecord;
import org.gasm.matos.entity.visitor.EquipmentVisitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Equipment extends AbstractStringEntity {
	  
	@Id
	private String reference;
	
	/**
	 * The jacket brand
	 */
	private Brand brand;

    @JsonIgnore
    private Ref<RentalRecord> rentalRecord;

    private List<Ref<RentHistory>> historyList;
    private final RentHistoryDao rentHistoryDao = new RentHistoryDao();

    public abstract String getType();

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

    public RentalRecord getRentalRecord() {
        return ObjectifyHelper.getItem(this.rentalRecord);
    }

    public void rent(RentalRecord rentalRecord) throws IllegalRentStatusException {

        if(getRentalRecord() != null && ObjectUtils.equals(getRentalRecord().getId(),rentalRecord.getId()))  {
            //On est en modification de location et sur la sur la même location on ne fait rien
        }
        else if (getRentalRecord() != null && !ObjectUtils.equals(getRentalRecord().getId(),rentalRecord.getId())) {
            //On change de rental Record et on n'a pas été rendu....
            throw new IllegalRentStatusException("Equipemnt reference[" + reference + "]Turn me In before rent me again !!!!!");
        }
        else {
            this.rentalRecord = ObjectifyHelper.getRef(rentalRecord);
            if(historyList == null ) {
                historyList = new ArrayList<>();
            }
            RentHistory rh = new RentHistory(new Date(), rentalRecord.getRenter(), rentalRecord.getDEvent().getBillingType());
            RentHistoryDao rentHistoryDao = new RentHistoryDao();
            rentHistoryDao.createOrUpdate(rh);

            historyList.add(ObjectifyHelper.getRef(rh));
        }
    }

    public void turnIn() {
        if(historyList == null || historyList.size() == 0) {
            System.err.println("Pas d'historique !!!!");
            /*throw new IllegalStateException("Pas d'historique !!!! on ne peut pas rendre");*/
        }
        else {
            int index = historyList.size() - 1;

            RentHistory rentHistory = ObjectifyHelper.getItem(historyList.get(index));
            rentHistory.setTurnInDate(new Date());
            rentHistoryDao.createOrUpdate(rentHistory);

        }
        this.rentalRecord = null;
    }

    public void cancelRent() throws IllegalRentStatusException {

        if(historyList == null || historyList.size() == 0) {
            System.err.println("Pas d'historique !!!!");
            /*throw new IllegalStateException("Pas d'historique !!!! on ne peut pas rendre");*/
        }
        else {
            int index = historyList.size() - 1;

            RentHistory rentHistory = ObjectifyHelper.getItem(historyList.get(index));
            if(rentHistory.getTurnInDate() != null) {
                throw new IllegalRentStatusException("Equipemnt reference[" + reference + "] - no open rent History !!!!!");
            }

            rentHistoryDao.delete(rentHistory.getId());

            historyList.remove(index);
        }
        this.rentalRecord = null;

    }

    public boolean isRented() {
        return  rentalRecord!=null;
    }

    public String getRenterFullName() {
        if(getRentalRecord() != null) {
            return getRentalRecord().getRenter().getFullName();
        }
        else {
            return null;
        }
    }

    public abstract void accept(EquipmentVisitor visitor);

    public abstract double getPrice();

    public List<RentHistory> getHistoryList() {
        return ObjectifyHelper.toList(historyList);
    }

}
