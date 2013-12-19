package org.gasm.persistance.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class AbstractStringIdDao<T extends Object> extends AbstractDao<T> {

	public T get(String id) {
		return ofy().load().type(getClazz()).id(id).now();
	}
	
	public void delete(String id) {
		ofy().delete().type(getClazz()).id(id).now();
	}

}
