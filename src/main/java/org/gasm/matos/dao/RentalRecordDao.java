package org.gasm.matos.dao;


import org.gasm.matos.entity.rental.RentalRecord;
import org.gasm.persistance.dao.AbstractLongIdDao;

public class RentalRecordDao extends AbstractLongIdDao<RentalRecord> {
	
	@Override
	protected Class<RentalRecord> getClazz() {
		return RentalRecord.class;
	}
}
