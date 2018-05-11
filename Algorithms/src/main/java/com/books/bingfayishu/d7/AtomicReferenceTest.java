package com.books.bingfayishu.d7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试用例有问题，待修复
 * 
 * @author zhangbo
 */
@Slf4j
public class AtomicReferenceTest {

    public static void main(String[] args) {

        AtomicReference<U> atomicU = new AtomicReference<>(new U("1", 1));
        
        U u = new U("1", 1);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new UpdateU(u));
        }
        for (int i = 0; i < 10; i++) {
            executorService.execute(new AtomicUpdateU(atomicU));
        }
        
        executorService.shutdown();

    }
    
    static class UpdateU implements Runnable {
        U u;

        public UpdateU(U u) {
            this.u = u;
        }

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            u.setAge((int) id);
            u.setName(String.valueOf(id));
            log.info("Thread id: {}, after update: {}", id, JSON.toJSONString(u));
        }
    }
    
    static class AtomicUpdateU implements Runnable {

        AtomicReference<U> atomicU;

        public AtomicUpdateU(AtomicReference<U> atomicU) {
            this.atomicU = atomicU;
        }

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
//            log.info("Thread id: {}, before atomic update: {}", id, JSON.toJSONString(atomicU));
            atomicU.updateAndGet((u) -> {
                u.setName(String.valueOf(id));
                u.setAge((int) id);

                return u;
            });
            
            log.info("Thread id: {}, after atomic update: {}", id, JSON.toJSONString(atomicU));
        }
    }
    
    static class U {
        private String name;
        private int age;

        public U(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
    
}
