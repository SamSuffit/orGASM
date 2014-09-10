package org.gasm.matos.rest.json;


import junit.framework.Assert;
import org.fest.assertions.Assertions;
import org.gasm.matos.entity.Jacket;
import org.gasm.matos.entity.enums.Brand;
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
public class JacketAPITest extends AbstractRESTAPITest {

    private Jacket j1;
    private JacketAPI api = new JacketAPI();

    @Before
    public void setUpTest() {

        j1 = createJacket("J1", Size.M, Brand.Autre);

        Assertions.assertThat(jacketDao.findAll())
                .isNotNull()
                .hasSize(1)
                .containsOnly(j1)
        ;

    }

    @Test
      public void testUpdate() throws ItemNotFoundException, IllegalRentStatusException {

        final String reference = "J1";

        Assertions.assertThat(jacketDao.findAll())
                .isNotNull()
                .hasSize(1);

        RentalRecord rentalRecord = createRentalRecord(dEvent, maxime);
        j1.rent(rentalRecord);
        jacketDao.createOrUpdate(j1);


        Jacket jacket = new Jacket();
        jacket.setReference(reference);
        jacket.setSize(Size.M);
        jacket.setBrand(Brand.Scubapro);
        Jacket result = api.createOrUpdate(jacket);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isRented());
        Assert.assertEquals(Size.M, result.getSize());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        //Test that object is correctly persist
        result = jacketDao.get(reference);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isRented());
        Assert.assertEquals(Size.M, result.getSize());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        Assertions.assertThat(jacketDao.findAll())
                .isNotNull()
                .hasSize(1);
    }

    @Test
    public void testCreate() throws ItemNotFoundException, IllegalRentStatusException {

        final String reference = "J3";

        Assertions.assertThat(jacketDao.findAll())
                .isNotNull()
                .hasSize(1);

        Jacket jacket = new Jacket();
        jacket.setReference(reference);
        jacket.setSize(Size.M);
        jacket.setBrand(Brand.Scubapro);
        Jacket result = api.createOrUpdate(jacket);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isRented());
        Assert.assertEquals(Size.M, result.getSize());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        //Test that object is correctly persist
        result = jacketDao.get(reference);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isRented());
        Assert.assertEquals(Size.M, result.getSize());
        Assert.assertEquals(Brand.Scubapro, result.getBrand());

        Assertions.assertThat(jacketDao.findAll())
                .isNotNull()
                .hasSize(2);
    }
}
