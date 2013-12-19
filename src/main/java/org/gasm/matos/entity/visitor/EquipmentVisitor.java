package org.gasm.matos.entity.visitor;

import org.gasm.matos.entity.*;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 15/11/13
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public interface EquipmentVisitor {

    public void visit(Jacket equipment);
    public void visit(Tank equipment);
    public void visit(Regulator equipment);
    public void visit(Suit equipment);

}
