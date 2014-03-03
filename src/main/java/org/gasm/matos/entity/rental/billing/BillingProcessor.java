package org.gasm.matos.entity.rental.billing;

import org.gasm.matos.entity.Equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 15/11/13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class BillingProcessor {

    private final BillingType billingType;
    private final Map<Class<? extends Equipment>,Double> pricePerTypeMap;

    public BillingProcessor(BillingType billingType) {
        pricePerTypeMap = new HashMap<>();
        this.billingType = billingType;
    }

    public double getPrice(Collection<? extends Equipment> equipments) {
        
        for(Equipment equipment : equipments) {
            if(pricePerTypeMap.containsKey(equipment.getClass())) {
                Double equipmentPrice = pricePerTypeMap.get(equipment.getClass());
                pricePerTypeMap.put(equipment.getClass(),new Double(Math.max(equipment.getPrice(), equipmentPrice)));
            }
            else {
                pricePerTypeMap.put(equipment.getClass(),equipment.getPrice());
            }
        }
        Double ret = new Double(0);
        for(Double value :pricePerTypeMap.values()) {
            ret += value;
        }

        if (billingType.getBillingThreshold() != null) {
            return (ret > billingType.getBillingThreshold() ?  billingType.getBillingThreshold() : ret);
        } else {
            return ret;
        }
    }


}
