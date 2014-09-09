package org.gasm.matos.rest.json;

import org.gasm.matos.entity.rental.billing.BillingType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/billing")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BillingAPI {
	
	@GET
    @Path("/billingType")
	public List<BillingType> list() {
		return Arrays.asList(BillingType.values());
	}

}
