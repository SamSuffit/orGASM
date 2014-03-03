package org.gasm.matos.entity;

import org.apache.commons.lang3.time.DateUtils;
import org.fest.assertions.Assertions;
import org.gasm.matos.entity.enums.Brand;
import org.gasm.matos.entity.enums.Size;
import org.gasm.matos.entity.exception.IllegalRentStatusException;
import org.gasm.matos.entity.rental.DivingEvent;
import org.gasm.matos.entity.rental.RentalRecord;
import org.gasm.matos.rest.exception.ItemNotFoundException;
import org.gasm.persistance.test.AbstractDatastoreTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 24/02/14
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class EquipmentTest extends AbstractDatastoreTest {


    private Adherent samuel;
    private Adherent maxime ;
    private Jacket j1;
    private Jacket j2;
    private DivingEvent dEvent;

    @Before
    public void setUp() {

        samuel = createAdherent("Samuel", "Santschi");
        maxime = createAdherent("Maxime", "Vialette");
        j1 = createJacket("J1", Size.M, Brand.Autre);
        j2 = createJacket("J2", Size.M, Brand.Autre);
        dEvent = createDivingEvent(samuel);

        Assertions.assertThat(adherentDao.findAll())
                .isNotNull()
                .hasSize(2)
                .containsOnly(samuel, maxime)
        ;

        Assertions.assertThat(jacketDao.findAll())
                .isNotNull()
                .hasSize(2)
                .containsOnly(j1,j2)
        ;

        Assertions.assertThat(divingEventDao.findAll())
                .isNotNull()
                .hasSize(1)
                .containsOnly(dEvent)
        ;
    }

    @Test
    public void testRentNormal() throws ItemNotFoundException, IllegalRentStatusException {
        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);

        assertFalse(j1.isRented());
        assertNull(j1.getRentalRecord());
        //Cas normal
        j1.rent(rentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);

        assertTrue(j1.isRented());
        assertEquals(rentalRecord, j1.getRentalRecord());

    }

    @Test
    public void testRentTwice() throws ItemNotFoundException, IllegalRentStatusException {
        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);

        //Cas normal
        j1.rent(rentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);

        //On ressoumet sur le même rental record => ok tjs 1 en hisotrique
        j1.rent(rentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);
    }

    @Test(expected = IllegalRentStatusException.class)
    public void testRentWithoutTurnIn() throws ItemNotFoundException, IllegalRentStatusException {
        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);
        RentalRecord otherRentalRecord = createRentalRecord(dEvent, maxime);

        //Cas normal
        j1.rent(rentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);

        //On soumet sans avoir libéré....
        j1.rent(otherRentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);
    }

    @Test
    public void testTurnIn() throws ItemNotFoundException, IllegalRentStatusException {
        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);

        assertFalse(j1.isRented());
        assertNull(j1.getRentalRecord());

        //Cas normal
        j1.rent(rentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);
        RentHistory history = j1.getHistoryList().get(0);
        assertTrue(DateUtils.isSameDay(new Date(), history.getRentDate()));
        assertNull( history.getTurnInDate());

        assertTrue(j1.isRented());
        assertEquals(rentalRecord, j1.getRentalRecord());

        j1.turnIn();

        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);
        assertFalse(j1.isRented());
        assertNull(j1.getRentalRecord());

        history = j1.getHistoryList().get(0);

        assertTrue(DateUtils.isSameDay(new Date(), history.getRentDate()));
        assertNotNull( history.getTurnInDate());
        assertTrue(DateUtils.isSameDay(new Date(), history.getTurnInDate()));

        Assertions.assertThat(rentHistoryDao.findAll())
                .isNotNull()
                .hasSize(1)
                .containsOnly(history)
        ;

    }

    @Test
    public void testCancelRent() throws ItemNotFoundException, IllegalRentStatusException {
        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);

        assertFalse(j1.isRented());
        assertNull(j1.getRentalRecord());

        //Cas normal
        j1.rent(rentalRecord);
        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(1);
        RentHistory history = j1.getHistoryList().get(0);
        assertTrue(DateUtils.isSameDay(new Date(), history.getRentDate()));
        assertNull( history.getTurnInDate());

        assertTrue(j1.isRented());
        assertEquals(rentalRecord, j1.getRentalRecord());

        j1.cancelRent();

        Assertions.assertThat(j1.getHistoryList())
                .isNotNull()
                .hasSize(0);
        assertFalse(j1.isRented());
        assertNull(j1.getRentalRecord());

    }

}
