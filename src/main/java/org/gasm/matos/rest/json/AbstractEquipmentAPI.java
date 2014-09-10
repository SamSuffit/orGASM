package org.gasm.matos.rest.json;

import org.gasm.matos.entity.AbstractStringEntity;
import org.gasm.matos.entity.Equipment;
import org.gasm.persistance.dao.AbstractStringIdDao;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 10/09/14
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEquipmentAPI<T extends Equipment> extends AbstractStringIdAPI<T> {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public T createOrUpdate(T newBean) {

        final AbstractStringIdDao<T> dao = getDao();

        T oldBean = dao.get(newBean.getReference());
        if(oldBean != null) {
            logger.info(
                    new StringBuilder("Updating json ignore properties for class [").append(newBean.getClass().getSimpleName()).append("]")
                            .append(" having reference [" ).append( newBean.getReference()).append( "]")
                            .toString());
            newBean.updateJSONIgnoreProperties(oldBean);
        }

        T createdBean = dao.createOrUpdate(newBean);
        return createdBean;
    }

}
