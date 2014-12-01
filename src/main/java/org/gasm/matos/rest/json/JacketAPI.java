package org.gasm.matos.rest.json;

import org.gasm.matos.dao.JacketDao;
import org.gasm.matos.entity.Jacket;
import org.gasm.persistance.dao.AbstractStringIdDao;

import javax.ws.rs.Path;

@Path("/jacket")
public class JacketAPI extends AbstractEquipmentAPI<Jacket> {
	
	JacketDao dao = new JacketDao();

	@Override
	public AbstractStringIdDao<Jacket> getDao() {
		return dao;
	}

}
