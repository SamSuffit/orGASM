package org.gasm.concurrency;

import junit.framework.Assert;
import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 27/02/14
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public class KeyMutexTest {

    @Test
    public void test() throws InterruptedException {
        KeyMutex.Mutex m = KeyMutex.getMutex("key");

        Assert.assertTrue(KeyMutex.usedKey.containsKey("key"));
        Assert.assertTrue(KeyMutex.usedKeyCounter.containsKey("key"));
        Assert.assertEquals(1,KeyMutex.usedKeyCounter.get("key").intValue());

        KeyMutex.Mutex m2 = KeyMutex.getMutex("key");

        Assert.assertTrue(KeyMutex.usedKey.containsKey("key"));
        Assert.assertTrue(KeyMutex.usedKeyCounter.containsKey("key"));
        Assert.assertEquals(2,KeyMutex.usedKeyCounter.get("key").intValue());
        Assert.assertTrue(m == m2);

        KeyMutex.acquireMutex(m);
        KeyMutex.releaseMutex(m);

        Assert.assertTrue(KeyMutex.usedKey.containsKey("key"));
        Assert.assertTrue(KeyMutex.usedKeyCounter.containsKey("key"));
        Assert.assertEquals(1,KeyMutex.usedKeyCounter.get("key").intValue());

        KeyMutex.acquireMutex(m2);
        KeyMutex.releaseMutex(m2);

        Assert.assertFalse(KeyMutex.usedKey.containsKey("key"));
        Assert.assertFalse(KeyMutex.usedKeyCounter.containsKey("key"));

    }
}
