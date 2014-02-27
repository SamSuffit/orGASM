package org.gasm.persistance.dao;

import org.gasm.matos.entity.AbstractStringEntity;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class AbstractStringIdDao<T extends AbstractStringEntity> extends AbstractDao<T> {

	public T get(String id) {
		return ofy().load().type(getClazz()).id(id).now();
	}
	
	public void delete(String id) {
		ofy().delete().type(getClazz()).id(id).now();
	}

}
