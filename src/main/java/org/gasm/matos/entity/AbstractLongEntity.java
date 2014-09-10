package org.gasm.matos.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 27/02/14
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractLongEntity extends AbstractEntity{

    public abstract Long getId();

    @JsonProperty("isCreated")
    public final boolean isCreated() {
        return getId() != null;
    }
}

