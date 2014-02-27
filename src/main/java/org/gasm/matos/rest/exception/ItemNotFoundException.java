package org.gasm.matos.rest.exception;

import org.gasm.matos.entity.AbstractEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 13/11/13
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }

    public <E extends AbstractEntity> ItemNotFoundException(Long id, Class<E> clazz) {
        super(new StringBuilder("No ").append(clazz != null ? clazz : "AbstractEntity").append(" with Id [").append(id).append("]").toString());
    }
}
