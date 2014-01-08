package org.gasm.matos.rest.json;

import org.gasm.matos.entity.AbstractEntity;
import org.gasm.persistance.dao.AbstractDao;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

public abstract class AbstractAPI<T extends AbstractEntity> {
	
	public abstract AbstractDao<T> getDao();

	public AbstractAPI() {
		super();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<T> list() {
		return getDao().findAll();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public T createOrUpdate(T newBean) {
		T createdBean = getDao().createOrUpdate(newBean);
		return createdBean;
	}

}