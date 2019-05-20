package com.zookeeper.source;

import org.apache.zookeeper.server.SessionTrackerImpl;
import org.apache.zookeeper.server.ZooKeeperServer;

/**
 * @see ZooKeeperServer#startup()
 * @see ZooKeeperServer#createSessionTracker()
 * @see SessionTrackerImpl#SessionTrackerImpl(org.apache.zookeeper.server.SessionTracker.SessionExpirer, java.util.concurrent.ConcurrentHashMap, int, long, org.apache.zookeeper.server.ZooKeeperServerListener)
 * @see SessionTrackerImpl#initializeNextSession(long)
 * 
 * @author zhangbo
 */
public class SessionIdTest {

    public static void main(String[] args) {
        
        long id = 255;

        long timeMillis = System.currentTimeMillis();

        long sid = timeMillis << 24;
        System.out.println("time \t= \t" + String.format("%64s", Long.toBinaryString(timeMillis)));
        System.out.println("<< 24 \t= \t" + String.format("%64s", Long.toBinaryString(sid)));
        System.out.println(">>> 8 \t= \t" + String.format("%64s", Long.toBinaryString(sid >>> 8)));
        System.out.println("id   \t= \t" + String.format("%64s", Long.toBinaryString(id)));
        System.out.println("id<<56 \t= \t" + String.format("%64s", Long.toBinaryString(id << 56)));

        long sessionId = initializeNextSession(id, timeMillis);
        System.out.println("sid \t= \t" + String.format("%64s", Long.toBinaryString(sessionId)));
        System.out.println(sessionId);
        
    }


    public static long initializeNextSession(long id, long timeMillis) {
        long nextSid = 0;
        nextSid = (timeMillis << 24) >>> 8;
        nextSid =  nextSid | (id << 56);
        return nextSid;
    }

    public static long initializeNextSession(long id) {
        long nextSid = 0;
        nextSid = (System.currentTimeMillis() << 24) >>> 8;
        nextSid =  nextSid | (id << 56);
        return nextSid;
    }
}
