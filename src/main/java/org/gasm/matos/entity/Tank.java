package org.gasm.matos.entity;

import com.googlecode.objectify.annotation.EntitySubclass;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.TankDao;
import org.gasm.matos.entity.enums.Capacity;
import org.gasm.matos.entity.visitor.EquipmentVisitor;
import org.gasm.persistance.dao.AbstractDao;

@EntitySubclass(index=true)
public class Tank extends Equipment {

	@Override
	public String getType() {
		return "Tank";
	}

    @JsonIgnore
    @Override
    public AbstractDao getDao() {
        return new TankDao();
    }

    @Override
    public void accept(EquipmentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double getPrice() {
        return getBlockCapacity() == Capacity.Litre_15 ? 6 : 4.5;
    }

    /**
     * The Tank capacity
     */
    private Capacity blockCapacity;

    public Capacity getBlockCapacity() {
        return blockCapacity;
    }

    public void setBlockCapacity(Capacity blockCapacity) {
        this.blockCapacity = blockCapacity;
    }
}
