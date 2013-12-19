package org.gasm.matos.rest.exception;

import org.gasm.matos.entity.Equipment;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 13/11/13
 * Time: 10:38
 * To change this template use File | Settings | File Templates.
 */
public class EquipmentNotFoundException extends ItemNotFoundException {

    public <T extends Equipment> EquipmentNotFoundException(String id, Class<T> clazz) {
        super(new StringBuilder("No ").append(clazz != null ? clazz : "Equipment").append(" with Id [").append(id).append("]").toString());
    }
}
