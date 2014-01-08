package org.gasm.matos.dao;

import org.gasm.matos.entity.Adherent;
import org.gasm.matos.entity.RentHistory;
import org.gasm.persistance.dao.AbstractLongIdDao;

public class RentHistoryDao extends AbstractLongIdDao<RentHistory> {
	
	@Override
	protected Class<RentHistory> getClazz() {
		return RentHistory.class;
	}


}
