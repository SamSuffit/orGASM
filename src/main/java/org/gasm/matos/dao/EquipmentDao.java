package org.gasm.matos.dao;

import org.gasm.matos.entity.Equipment;
import org.gasm.persistance.dao.AbstractStringIdDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EquipmentDao extends AbstractStringIdDao<Equipment> {
	
	@Override
	protected Class<Equipment> getClazz() {
		return Equipment.class;
	}

    public List<Equipment> findAllRented() {
        List<Equipment> ret = new ArrayList<>();
        for(Equipment e : findAll()) {
            if(e.isRented()) {
                ret.add(e);
            }
        }
        Collections.sort(ret, new Comparator<Equipment>(){
            @Override
            public int compare(Equipment o1, Equipment o2) {
                int typeComp =o1.getRenterFullName().compareTo(o2.getRenterFullName());
                if(typeComp == 0 ){
                    return   o1.getReference().compareTo(o2.getReference());
                }
                else {
                    return typeComp;
                }
            }
        });
        return ret;
    }
}
