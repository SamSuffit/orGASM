package org.gasm.concurrency;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: SANTSCHISA
 * Date: 27/02/14
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class KeyMutex {

    private static final Semaphore mutex;
    protected static final Map<String,Mutex> usedKey;
    protected static final Map<String,Integer> usedKeyCounter;

    static {
        mutex = new Semaphore(1);
        usedKey = new HashMap<>();
        usedKeyCounter = new HashMap<>();
    }

    public static Mutex getMutex(String key) throws InterruptedException {
        mutex.acquire();
        Mutex ret = null;

        if(usedKey.containsKey(key)) {
            ret = usedKey.get(key);
            usedKeyCounter.put(key,new Integer(usedKeyCounter.get(key) +1 ));
        }
        else {
            ret = new Mutex(key);
            usedKeyCounter.put(key,new Integer(1));
        }
        usedKey.put(key,ret);
        mutex.release();
        return ret;
    }

    public static void acquireMutex(Mutex m) throws InterruptedException  {
        m.semaphore.acquire();
    }

    public static void releaseMutex(Mutex m) throws InterruptedException  {
        mutex.acquire();
        final String key = m.key;
        Integer count = usedKeyCounter.get(key);
        if(count -1 == 0) {
            usedKeyCounter.remove(key);
            usedKey.remove(key);
        }
        else {
            usedKeyCounter.put(key,new Integer(usedKeyCounter.get(key) -1 ));
        }
        mutex.release();
        m.semaphore.release();
    }

    public static class Mutex {
        private final String key;
        private final Semaphore semaphore;

        public Mutex(String key) {
            this.semaphore = new Semaphore(1);
            this.key = key;
        }
    }
}
