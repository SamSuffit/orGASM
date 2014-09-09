package org.gasm.matos.dao;

import org.gasm.matos.entity.Jacket;
import org.gasm.persistance.dao.AbstractStringIdDao;

public class JacketDao extends AbstractStringIdDao<Jacket> {
	
	@Override
	protected Class<Jacket> getClazz() {
		return Jacket.class;
	}


}
