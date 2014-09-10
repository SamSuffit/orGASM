package org.gasm.matos.rest.json;

import org.gasm.matos.dao.WetsuitDao;
import org.gasm.matos.entity.Suit;
import org.gasm.persistance.dao.AbstractStringIdDao;

import javax.ws.rs.Path;

@Path("/suit")
public class SuitAPI extends AbstractEquipmentAPI<Suit> {
	
	WetsuitDao dao = new WetsuitDao();

	@Override
	public AbstractStringIdDao<Suit> getDao() {
		return dao;
	}

}
