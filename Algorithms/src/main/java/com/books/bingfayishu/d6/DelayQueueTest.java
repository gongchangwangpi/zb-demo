package com.books.bingfayishu.d6;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 延迟队列，可以为每个元素设置延迟时间，
 * 只有达到指定的延迟时间时，才能从队列中获取到元素
 * 
 * @author zhangbo
 */
public class DelayQueueTest {

    public static void main(String[] args) {

        DelayQueue<Element> delayQueue = new DelayQueue<>();

        delayQueue.offer(new Element("123"));
        delayQueue.offer(new Element("234"));
        delayQueue.offer(new Element("345"));

        // Element设计为默认延迟2秒
        SleepUtils.second(3);
        
        System.out.println(delayQueue.poll().getContent());
        System.out.println(delayQueue.poll().getContent());
        System.out.println(delayQueue.poll().getContent());

    }
    
    
    static class Element implements Delayed {
        // 默认延迟2秒
        private static final long delay = 2000L * 1000 * 1000;
        // 元素内容
        private String content;
        // 元素创建时间的纳秒,用于计算延迟时间
        private long create;

        public Element(String content) {
            this.content = content;
            this.create = System.nanoTime();
            System.out.println(create);
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            // 返回元素从创建开始，与默认延迟时间的差
            return unit.toNanos(delay) - (System.nanoTime() - create);
        }

        /**
         * 实现此方法时，可以将延迟时间最长的，放在队列末尾
         * 
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
//            if (o instanceof Element) {
//                return this.content.compareTo(((Element) o).content);
//            }
//            return 0;
            if (this == o) {
                return 0;
            }
            long d = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }
    
}
