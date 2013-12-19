package org.gasm.matos.entity.helper;

import com.googlecode.objectify.Ref;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 12/11/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class ObjectifyHelper {

    public static <E> Set<E> toSet(Set<Ref<E>> records) {
        if (records != null) {
            HashSet<E> ret= new HashSet<E>(records.size());
            for(Ref<E> recRef: records) {
                E record = recRef.get();
                //Used if some dumbass like Samuel has kill so key in the store..
                if(record != null) {
                    ret.add(record);
                }
            }
            return ret;
        } else {
            return new HashSet<E>(0);
        }
    }

    public static <E> List<E> toList(List<Ref<E>> records) {
        if (records != null) {
            ArrayList<E> ret= new ArrayList<E>(records.size());
            for(Ref<E> recRef: records) {
                E record = recRef.get();
                //Used if some dumbass like Samuel has kill so key in the store..
                if(record != null) {
                    ret.add(record);
                }
            }
            return ret;
        } else {
            return Collections.emptyList();
        }
    }

    public static <E> E getItem(Ref<E> itemRef) {
        return  (itemRef != null ? itemRef.get() : null);
    }

    public static <E> Ref<E> getRef(E item) {
        return (item != null ? Ref.create(item) : null);
    }


}
