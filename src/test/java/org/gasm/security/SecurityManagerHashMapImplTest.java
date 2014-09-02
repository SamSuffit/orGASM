package org.gasm.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 02/09/14
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class SecurityManagerHashMapImplTest {

    SecurityManagerHashMapImpl securityManager;

    @Before
    public void init() {
        securityManager = SecurityManagerHashMapImpl.getInstance();
        securityManager.reset();
    }

    @Test
    public void testIsValid() {
        Assert.assertFalse(securityManager.isValid("dsfds"));
        Assert.assertFalse(securityManager.isValid("fdsfsdf"));

        String fistCall = securityManager.getSecurityKey("admin", "plongée");
        Assert.assertNotNull(fistCall);

        Assert.assertFalse(securityManager.isValid("dsfds"));
        Assert.assertTrue(securityManager.isValid(fistCall));
    }


    @Test
    public void testUnicityOfKey() {

        String fistCall = securityManager.getSecurityKey("admin", "plongée");
        Assert.assertNotNull(fistCall);

        String secondCall = securityManager.getSecurityKey("admin", "plongée");
        Assert.assertEquals(fistCall,secondCall);
    }

    @Test
    public void testBadLogin() {
        Assert.assertNull(securityManager.getSecurityKey("aa", "bb"));
    }
}
