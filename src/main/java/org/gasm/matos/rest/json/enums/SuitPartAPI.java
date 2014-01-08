package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.enums.Brand;
import org.gasm.matos.entity.enums.SuitPart;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/suitPart")
public class SuitPartAPI {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<SuitPart> list() {
		return Arrays.asList(SuitPart.values());
	}

}
