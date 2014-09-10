package org.gasm.matos.entity;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 27/02/14
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractStringEntity extends AbstractEntity{

    public abstract String getReference();

    @JsonProperty("isCreated")
    public final boolean isCreated() {
        return StringUtils.isNotBlank(getReference());
    }
}

