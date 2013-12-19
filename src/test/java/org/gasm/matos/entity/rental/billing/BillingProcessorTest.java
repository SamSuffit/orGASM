package org.gasm.matos.entity.rental.billing;

import junit.framework.Assert;
import org.gasm.matos.entity.Suit;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 22/11/13
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */

public class BillingProcessorTest {

    @Test
    public void testDivingEventWithNoThreshold() {
        BillingProcessor bp = new BillingProcessor(BillingType.Standard);
        final Suit wetsuit = new Suit();
        Assert.assertEquals(wetsuit.getPrice(), bp.getPrice(Arrays.asList(wetsuit)));
    }

    @Test
    public void testDivingEventWithThreshold() {
        BillingProcessor bp = new BillingProcessor(BillingType.Chamagnieu);
        final Suit wetsuit = new Suit();
        Assert.assertEquals(BillingType.Chamagnieu.getBillingThreshold(),bp.getPrice(Arrays.asList(wetsuit)));
    }

}
