package org.gasm.matos.rest.json;


import junit.framework.Assert;
import org.fest.assertions.Assertions;
import org.gasm.matos.entity.Tank;
import org.gasm.matos.entity.enums.Brand;
import org.gasm.matos.entity.enums.Capacity;
import org.gasm.matos.entity.enums.Size;
import org.gasm.matos.entity.exception.IllegalRentStatusException;
import org.gasm.matos.entity.rental.RentalRecord;
import org.gasm.matos.rest.exception.ItemNotFoundException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 10/09/14
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class TankAPITest extends AbstractRESTAPITest {

    private Tank t1;
    private TankAPI api = new TankAPI();
    final String reference = "T1";

    @Before
    public void setUpTest() {

        t1 = createTank(reference, Capacity.Litre_12, Brand.Autre);

        Assertions.assertThat(tankDao.findAll())
                .isNotNull()
                .hasSize(1)
                .containsOnly(t1)
        ;

    }

    @Test
      public void testUpdate() throws ItemNotFoundException, IllegalRentStatusException {

        Assertions.assertThat(tankDao.findAll())
                .isNotNull()
                .hasSize(1);

        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);
        t1.rent(rentalRecord);
        tankDao.createOrUpdate(t1);

        Tank tank = new Tank();
        tank.setReference(reference);
        tank.setCapacity(Capacity.Litre_15);
        tank.setBrand(Brand.Scubapro);
        Tank result = api.createOrUpdate(tank);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isRented());
        Assert.assertEquals(Capacity.Litre_15, result.getCapacity());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        //Test that object is correctly persist
        result = tankDao.get(reference);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isRented());
        Assert.assertEquals(Capacity.Litre_15, result.getCapacity());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        Assertions.assertThat(tankDao.findAll())
                .isNotNull()
                .hasSize(1);
    }

    @Test
    public void testCreate() throws ItemNotFoundException, IllegalRentStatusException {

        final String newReference = "T3";

        Assertions.assertThat(tankDao.findAll())
                .isNotNull()
                .hasSize(1);

        Tank tank = new Tank();
        tank.setReference(newReference);
        tank.setCapacity(Capacity.Litre_15);
        tank.setBrand(Brand.Scubapro);
        Tank result = api.createOrUpdate(tank);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isRented());
        Assert.assertEquals(Capacity.Litre_15, result.getCapacity());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        //Test that object is correctly persist
        result = tankDao.get(newReference);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isRented());
        Assert.assertEquals(Capacity.Litre_15, result.getCapacity());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        Assertions.assertThat(tankDao.findAll())
                .isNotNull()
                .hasSize(2);
    }
}
