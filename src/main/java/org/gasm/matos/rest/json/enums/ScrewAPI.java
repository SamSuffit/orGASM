package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.enums.Screw;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gibson
 * Date: 08/07/14
 * Time: 07:11
 * To change this template use File | Settings | File Templates.
 */
@Path("/screw")
public class ScrewAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Screw> list() {
        return Arrays.asList(Screw.values());
    }

}
