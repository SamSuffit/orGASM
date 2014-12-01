package org.gasm.matos.rest.json;

import org.fest.assertions.Assertions;
import org.gasm.matos.entity.Adherent;
import org.gasm.matos.entity.Jacket;
import org.gasm.matos.entity.enums.Brand;
import org.gasm.matos.entity.enums.Size;
import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.persistance.test.AbstractDatastoreTest;
import org.junit.Before;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 10/09/14
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRESTAPITest extends AbstractDatastoreTest {

    protected Adherent samuel;
    protected Adherent maxime ;
    protected DivingEvent dEvent;

    @Before
    public void setUp() {

        samuel = createAdherent("Samuel", "Santschi");
        maxime = createAdherent("Maxime", "Vialette");

        dEvent = createDivingEvent(samuel);

        Assertions.assertThat(adherentDao.findAll())
                .isNotNull()
                .hasSize(2)
                .containsOnly(samuel, maxime)
        ;

        Assertions.assertThat(divingEventDao.findAll())
                .isNotNull()
                .hasSize(1)
                .containsOnly(dEvent)
        ;
    }
}
