package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.rental.entity.Payment;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/payment")
public class PayementAPI {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Payment> list() {
		return Arrays.asList(Payment.values());
	}

}
