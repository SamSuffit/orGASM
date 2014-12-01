package org.gasm.matos.rest.json.enums;

import org.gasm.matos.entity.enums.Material;

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
@Path("/material")
public class MaterialAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Material> list() {
        return Arrays.asList(Material.values());
    }

}
