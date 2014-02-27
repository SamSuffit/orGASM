package org.gasm.matos.entity.rental;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.gasm.matos.dao.DivingEventDao;
import org.gasm.matos.entity.AbstractLongEntity;
import org.gasm.matos.entity.Adherent;
import org.gasm.matos.entity.helper.ObjectifyHelper;
import org.gasm.matos.entity.rental.billing.BillingType;
import org.gasm.matos.rest.exception.ItemNotFoundException;
import org.gasm.persistance.dao.AbstractDao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class DivingEvent extends AbstractLongEntity {

    public DivingEvent() {
        billingType = BillingType.Standard;
    }

    @Override
    @JsonIgnore
    public AbstractDao getDao() {
        return new DivingEventDao();
    }
	
	@Id
	private Long id; 
	
	private String place;
	
	private Date date;
	
	private Ref<Adherent> inCharge;

    private BillingType billingType;

    private Set<Ref<RentalRecord>> rentalRecords;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	} 
	
	public Adherent getInCharge() {
		return ObjectifyHelper.getItem(inCharge);
	}

	public void setInCharge(Adherent inCharge) {
		this.inCharge = ObjectifyHelper.getRef(inCharge) ;
	}

    public Set<RentalRecord> getRentalRecords() {
        return ObjectifyHelper.toSet(rentalRecords);
    }

    public void addRentalRecord(RentalRecord record) {
        if(rentalRecords == null) {
            rentalRecords = new HashSet<Ref<RentalRecord>>();
        }
        rentalRecords.add(Ref.create(record));
    }

    public void removeRentalRecord(RentalRecord record) {
        if(rentalRecords != null && rentalRecords.contains(ObjectifyHelper.getRef(record))) {
            rentalRecords.remove(ObjectifyHelper.getRef(record));
        }
    }

    /**
     * Get current Rental Record for the given renterId, or create one if not existing
     * @param renterId
     * @return
     * @throws ItemNotFoundException, when renterId doesn't fit an actual adherent
     */
    public RentalRecord getRentalRecord(Long renterId) throws ItemNotFoundException {
        for(RentalRecord record: getRentalRecords()) {
            if(ObjectUtils.equals(record.getRenter().getId(), renterId)) {
                return record;
            }
        }
        final RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setDEvent(this);
        rentalRecord.setRenter(new Adherent(renterId));
        return rentalRecord;
    }

    public BillingType getBillingType() {
        return billingType;
    }

    public void setBillingType(BillingType billingType) {
        this.billingType = billingType;
    }
}
