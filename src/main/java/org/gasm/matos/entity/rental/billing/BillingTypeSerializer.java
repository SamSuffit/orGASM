package org.gasm.matos.entity.rental.billing;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 20/11/13
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
public class BillingTypeSerializer extends JsonSerializer<BillingType> {

    @Override
    public void serialize(BillingType billingType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("label", billingType.name());
        jsonGenerator.writeObjectField("billingThreshold", billingType.getBillingThreshold());
        jsonGenerator.writeEndObject();

    }
}
