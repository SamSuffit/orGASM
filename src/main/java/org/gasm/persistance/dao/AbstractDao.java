package org.gasm.persistance.dao;

import org.gasm.matos.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public abstract class AbstractDao<T extends AbstractEntity> {

    protected ArrayList<T> waitingToBeSave = new ArrayList<T>();

    synchronized ArrayList<T> getWaitingToBeSave() {
        return waitingToBeSave;
    }

    synchronized void setWaitingToBeSave(ArrayList<T> tArrayList) {
         waitingToBeSave = tArrayList;
    }

    protected abstract Class<T> getClazz();

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


