package com.zookeeper.keyvalue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author zhangbo
 */
@Slf4j
public class MyWatcher implements Watcher {

    ActiveKeyValueStore keyValueStore;

    public MyWatcher(String host) throws IOException, InterruptedException {
        this.keyValueStore = new ActiveKeyValueStore();
        keyValueStore.connect(host);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeDataChanged) {
            try {
                get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void get() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        String value = keyValueStore.get(Updater.KEY, this);
        log.info("get value: {}", value);
    }

    public static void main(String[] args) throws Exception {
        MyWatcher watcher = new MyWatcher("172.18.8.22");
        watcher.get();
        SleepUtils.second(Long.MAX_VALUE);
    }
}
