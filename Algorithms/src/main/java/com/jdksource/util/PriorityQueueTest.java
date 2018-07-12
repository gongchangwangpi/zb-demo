package com.jdksource.util;

import java.util.PriorityQueue;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 优先队列
 * 
 * @author zhangbo
 */
@Slf4j
public class PriorityQueueTest {

    public static void main(String[] args) {

        PriorityQueue<User> priorityQueue = new PriorityQueue<>();
        
        priorityQueue.offer(new User(4L, "u4"));
        priorityQueue.offer(new User(1L, "u1"));
        priorityQueue.offer(new User(3L, "u3"));
        priorityQueue.offer(new User(2L, "u2"));
        
        while (!priorityQueue.isEmpty()) {
            User user = priorityQueue.poll();
            if (user != null) {
                log.info(JSON.toJSONString(user));
            }
        }
        
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class User implements Comparable<User> {
        private Long id;
        private String username;

        /**
         * 此处只是简单的以id比较
         * @param o
         * @return
         */
        @Override
        public int compareTo(User o) {
            return (int) (this.id - o.id);
        }
    }
    
}
