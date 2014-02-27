package org.gasm.matos.entity.rental;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.gasm.matos.dao.RentalRecordDao;
import org.gasm.matos.entity.AbstractLongEntity;
import org.gasm.matos.entity.Adherent;
import org.gasm.matos.entity.Equipment;
import org.gasm.matos.entity.helper.ObjectifyHelper;
import org.gasm.matos.entity.rental.billing.BillingProcessor;
import org.gasm.matos.entity.rental.entity.Payment;
import org.gasm.matos.rest.exception.ItemNotFoundException;
import org.gasm.persistance.dao.AbstractDao;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentalRecord extends AbstractLongEntity {

    @Override
    @JsonIgnore
    public AbstractDao getDao() {
        return new RentalRecordDao();
    }
	
	@Id
	private Long id; 
	
	private Ref<Adherent> renter;
	
	private Set<Ref<Equipment>> equipments;

    private Ref<DivingEvent> dEvent;

    private Payment payment;

    private boolean paid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Adherent getRenter() {
		return (renter != null ? renter.get() : null);
	}

	public void setRenter(Adherent renter) throws ItemNotFoundException {
        Ref<Adherent> renterRef = (renter != null ? Ref.create(renter) : null);
        if  (renterRef.get() == null) {
          throw new ItemNotFoundException(renter.getId(),Adherent.class);
        }
        this.renter = renterRef;
	}
	
	public void addEquipment(Equipment equipment) {
		if(equipment == null) {
			throw new NullPointerException("added equipment can't be null");
		}
		if(equipments == null) {
			equipments = new HashSet<Ref<Equipment>>();
		}
		equipments.add(Ref.create(equipment));
	}
	
	public Set<Equipment> getEquipments() {
        return ObjectifyHelper.toSet(equipments);
	}

    @JsonIgnore
    public DivingEvent getDEvent() {
        return ObjectifyHelper.getItem(dEvent);
    }

    public void setDEvent(DivingEvent dEvent) {
        this.dEvent = ObjectifyHelper.getRef(dEvent);
    }

    public double getPrice() {
        return new BillingProcessor(getDEvent().getBillingType()).getPrice(getEquipments());
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
