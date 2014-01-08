package org.gasm.matos.entity;

import com.googlecode.objectify.annotation.EntitySubclass;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.gasm.matos.dao.WetsuitDao;
import org.gasm.matos.entity.enums.Size;
import org.gasm.matos.entity.enums.SuitPart;
import org.gasm.matos.entity.visitor.EquipmentVisitor;
import org.gasm.persistance.dao.AbstractDao;

@EntitySubclass(index=true)
public class Suit extends Equipment {

    /**
     * The Jacket size
     */
    private Size size;

    private SuitPart suitPart;

	@Override
	public String getType() {
		return "Suit";
	}

    @Override
    public void accept(EquipmentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double getPrice() {
        return 6;
    }

    @JsonIgnore
    @Override
    public AbstractDao getDao() {
        return new WetsuitDao();
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public SuitPart getSuitPart() {
        return suitPart;
    }

    public void setSuitPart(SuitPart suitPart) {
        this.suitPart = suitPart;
    }
}
