package org.gasm.matos.rest.json;

import org.gasm.matos.dao.RegulatorDao;
import org.gasm.matos.entity.Regulator;
import org.gasm.persistance.dao.AbstractStringIdDao;

import javax.ws.rs.Path;

@Path("/regulator")
public class RegulatorAPI extends AbstractStringIdAPI<Regulator> {
	
	RegulatorDao dao = new RegulatorDao();

	@Override
	public AbstractStringIdDao<Regulator> getDao() {
		return dao;
	}

}
