package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.enums.Size;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/size")
public class SizeAPI  {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Size> list() {
		return Arrays.asList(Size.values());
	}

}
