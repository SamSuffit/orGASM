package org.gasm.matos.entity;

import com.googlecode.objectify.annotation.EntitySubclass;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.JacketDao;
import org.gasm.matos.entity.enums.Size;
import org.gasm.matos.entity.visitor.EquipmentVisitor;
import org.gasm.persistance.dao.AbstractDao;

@EntitySubclass(index=true)
public class Jacket extends Equipment {

    @JsonIgnore
    @Override
    public AbstractDao getDao() {
        return new JacketDao();
    }
	
	/**
	 * The Jacket size
	 */
	private Size size;
	
	@Override
	public String getType() {
		return "Jacket";
	}

    @Override
    public void accept(EquipmentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double getPrice() {
        return 4.5;
    }

    public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

}
