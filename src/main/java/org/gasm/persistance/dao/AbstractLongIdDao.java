package org.gasm.persistance.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class AbstractLongIdDao<T extends Object> extends AbstractDao<T> {

	public T get(Long id) {
		return ofy().load().type(getClazz()).id(id).now();
	}
	
	public void delete(Long id) {
		ofy().delete().type(getClazz()).id(id).now();
	}

    public boolean exists(Long id) {
        return get(id) != null;
    }
}
