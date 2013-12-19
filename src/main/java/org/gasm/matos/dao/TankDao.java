package org.gasm.matos.dao;

import org.gasm.matos.entity.Tank;
import org.gasm.persistance.dao.AbstractStringIdDao;

public class TankDao extends AbstractStringIdDao<Tank> {
	
	@Override
	protected Class<Tank> getClazz() {
		return Tank.class;
	}


}
