package org.gasm.persistance.dao;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class AbstractDao<T extends Object> {
	
	protected abstract  Class<T> getClazz();
	
	public T createOrUpdate(T entity) {
		ofy().save().entity(entity).now();
		return entity;
	}
	
	public List<T> findAll() {
		return ofy().load().type(getClazz()).list();
		
	}
	
	public List<T> findAllSortedBy(String order) {
		return ofy().load().type(getClazz()).order(order).list();
	}
	

}


