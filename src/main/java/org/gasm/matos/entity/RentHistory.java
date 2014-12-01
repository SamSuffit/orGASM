package org.gasm.matos.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.sun.jersey.json.impl.JSONMarshallerImpl;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.gasm.matos.dao.RentHistoryDao;
import org.gasm.matos.entity.helper.ObjectifyHelper;
import org.gasm.matos.entity.rental.billing.BillingType;
import org.gasm.persistance.dao.AbstractDao;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 11/12/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class RentHistory extends AbstractLongEntity {

    @Id
    private Long id;

    private Date rentDate;

    private Date turnInDate;

    private Ref<Adherent> renter;

    @JsonIgnore
    private BillingType billingType;

    public RentHistory(Date rentDate, Adherent renter, BillingType billingType) {
        this.rentDate = rentDate;
        this.renter = ObjectifyHelper.getRef(renter);
        this.billingType = billingType;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public Date getTurnInDate() {
        return turnInDate;
    }

    public Adherent getRenter() {
        return ObjectifyHelper.getItem(renter);
    }

    public void setRenter(Adherent renter) {
        this.renter = ObjectifyHelper.getRef(renter);
    }

    @JsonProperty("billingType")
    public BillingType getBillingType() {
        return billingType;
    }

    public void setTurnInDate(Date turnInDate) {
        this.turnInDate = turnInDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //----- Used for objectify

    private RentHistory() {
    }

    @Override
    public AbstractDao getDao() {
        return new RentHistoryDao();
    }
}
