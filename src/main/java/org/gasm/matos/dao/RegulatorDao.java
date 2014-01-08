package org.gasm.matos.dao;

import org.gasm.matos.entity.Regulator;
import org.gasm.persistance.dao.AbstractStringIdDao;

public class RegulatorDao extends AbstractStringIdDao<Regulator> {
	
	@Override
	protected Class<Regulator> getClazz() {
		return Regulator.class;
	}


}
