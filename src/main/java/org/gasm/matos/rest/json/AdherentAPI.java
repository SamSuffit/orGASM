package org.gasm.matos.rest.json;

import org.gasm.matos.dao.AdherentDao;
import org.gasm.matos.entity.Adherent;
import org.gasm.persistance.dao.AbstractLongIdDao;

import javax.ws.rs.Path;

@Path("/adherent")
public class AdherentAPI extends AbstractLongIdAPI<Adherent> {
	
	AdherentDao dao = new AdherentDao();

	@Override
	public AbstractLongIdDao<Adherent> getDao() {
		return dao;
	}

}
