package org.gasm.matos.dao;


import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.persistance.dao.AbstractLongIdDao;

public class DivingEventDao extends AbstractLongIdDao<DivingEvent> {
	
	@Override
	protected Class<DivingEvent> getClazz() {
		return DivingEvent.class;
	}
}
