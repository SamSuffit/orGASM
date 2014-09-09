package org.gasm.matos.dao;

import org.gasm.matos.entity.Adherent;
import org.gasm.persistance.dao.AbstractLongIdDao;

public class AdherentDao extends AbstractLongIdDao<Adherent> {
	
	@Override
	protected Class<Adherent> getClazz() {
		return Adherent.class;
	}


}
