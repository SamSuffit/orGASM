package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.enums.Brand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/brand")
public class BrandAPI  {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Brand> list() {
		return Arrays.asList(Brand.values());
	}

}
