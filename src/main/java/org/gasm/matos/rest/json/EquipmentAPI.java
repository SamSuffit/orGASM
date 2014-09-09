package org.gasm.matos.rest.json;

import org.gasm.matos.dao.EquipmentDao;
import org.gasm.matos.entity.Equipment;
import org.gasm.matos.entity.exception.IllegalRentStatusException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/equipment")
public class EquipmentAPI {

    EquipmentDao dao = new EquipmentDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Equipment> list() {
        return dao.findAll();
    }

    @GET
    @Path("findAllRented")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Equipment> findAllRented() {
        return dao.findAllRented();
    }

    @PUT
    @Path("{id}/turnIn")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public void turnIn(@PathParam("id") String id) {
        Equipment equipment = dao.get(id);
        equipment.turnIn();
        dao.createOrUpdate(equipment);
    }

    @PUT
    @Path("{id}/cancelRent")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public void cancelRent(@PathParam("id") String id) throws IllegalRentStatusException {
        Equipment equipment = dao.get(id);
        equipment.cancelRent();
        dao.createOrUpdate(equipment);
    }

}
