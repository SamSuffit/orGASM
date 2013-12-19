package org.gasm.matos.dao;

import org.gasm.matos.entity.Suit;
import org.gasm.persistance.dao.AbstractStringIdDao;

public class WetsuitDao extends AbstractStringIdDao<Suit> {
	
	@Override
	protected Class<Suit> getClazz() {
		return Suit.class;
	}


}
