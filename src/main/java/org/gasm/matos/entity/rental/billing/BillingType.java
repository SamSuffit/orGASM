package org.gasm.matos.entity.rental.billing;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 20/11/13
 * Time: 09:28
 * To change this template use File | Settings | File Templates.
 */
@JsonSerialize(using = BillingTypeSerializer.class)
@JsonDeserialize(using = BillingTypeDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum BillingType {

    Standard(null)
    ,Chamagnieu(4.5d)
    ,Gratuit(0d)
    ;

    private BillingType(Double billingThreshold) {
         this.billingThreshold = billingThreshold;
    }

    private Double billingThreshold;

    public Double getBillingThreshold() {
        return billingThreshold;
    }


}
