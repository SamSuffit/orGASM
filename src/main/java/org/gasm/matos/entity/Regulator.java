package org.gasm.matos.entity;

import com.googlecode.objectify.annotation.EntitySubclass;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.RegulatorDao;
import org.gasm.matos.entity.visitor.EquipmentVisitor;
import org.gasm.persistance.dao.AbstractDao;

@EntitySubclass(index=true)
public class Regulator extends Equipment {

	@Override
	public String getType() {
		return "Regulator";
	}

    @Override
    public void accept(EquipmentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double getPrice() {
        return 4.5;
    }

    @JsonIgnore
    @Override
    public AbstractDao getDao() {
        return new RegulatorDao();
    }
}
