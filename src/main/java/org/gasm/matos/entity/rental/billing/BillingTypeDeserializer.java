package org.gasm.matos.entity.rental.billing;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 28/11/13
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class BillingTypeDeserializer extends JsonDeserializer<BillingType> {

    @Override
    public BillingType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        System.out.println("What the heck");
        while(jsonParser.hasCurrentToken()
                && jsonParser.getCurrentToken() != JsonToken.VALUE_STRING
                && !jsonParser.getCurrentName().equals("label") ) {
            jsonParser.nextValue();
        }
        if(jsonParser.hasCurrentToken()) {
            final BillingType billingType = BillingType.valueOf(jsonParser.getText());
            System.out.println("Renvoi: " + billingType);
            return billingType;
        }
        return BillingType.Standard;
    }
}
