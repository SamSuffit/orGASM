package org.gasm.matos.rest.json;

import org.gasm.matos.dao.TankDao;
import org.gasm.matos.entity.Tank;
import org.gasm.persistance.dao.AbstractStringIdDao;

import javax.ws.rs.Path;

@Path("/tank")
public class TankAPI extends AbstractEquipmentAPI<Tank> {
	
	TankDao dao = new TankDao();

	@Override
	public AbstractStringIdDao<Tank> getDao() {
		return dao;
	}

}
