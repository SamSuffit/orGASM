package org.gasm.matos.entity;

/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 27/02/14
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractLongEntity extends AbstractEntity{

    public abstract Long getId();

    public final boolean isCreated() {
        return getId() != null;
    }
}

