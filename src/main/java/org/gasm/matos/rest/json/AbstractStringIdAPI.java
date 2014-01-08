package org.gasm.matos.rest.json;

import org.gasm.matos.entity.AbstractEntity;
import org.gasm.persistance.dao.AbstractStringIdDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public abstract class AbstractStringIdAPI<T extends AbstractEntity> extends AbstractAPI<T> {
	
	public abstract AbstractStringIdDao<T> getDao();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public T get(@PathParam("id") String id) {
		return getDao().get(id);
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public void delete(@PathParam("id") String id) {
        getDao().delete(id);
	}
	
}
