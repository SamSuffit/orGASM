package org.gasm.matos.rest.json;

import org.gasm.matos.dao.DivingEventDao;
import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.persistance.dao.AbstractLongIdDao;

import javax.ws.rs.Path;

@Path("/divingEvent")
public class DivingEventAPI extends AbstractLongIdAPI<DivingEvent> {
	
	DivingEventDao dao = new DivingEventDao();

	@Override
	public AbstractLongIdDao<DivingEvent> getDao() {
		return dao;
	}

}
