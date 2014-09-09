package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.enums.Capacity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/capacity")
public class CapacityAPI {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Capacity> list() {
		return Arrays.asList(Capacity.values());
	}

}
