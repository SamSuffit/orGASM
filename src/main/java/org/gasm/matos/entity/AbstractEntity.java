package org.gasm.matos.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.persistance.dao.AbstractDao;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 13/11/13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEntity {

    @JsonIgnore
    public abstract boolean isCreated();

    @JsonIgnore
    public abstract AbstractDao getDao();

    @SuppressWarnings("unchecked")
    public void save() {
        getDao().createOrUpdate(this);
    }

}
