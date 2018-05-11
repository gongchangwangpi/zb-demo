package com.springapp.redis_pubsub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Created by books on 2017/11/6.
 */
@Configuration
public class RedisPubSubConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName("172.18.8.22");
        redisConnectionFactory.setPassword("jintoufs");
        redisConnectionFactory.setUsePool(true);
        return redisConnectionFactory;
    }
    
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter,
                                            MessageListenerAdapter listenerAdapter2) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("redisChatTest"));
        container.addMessageListener(listenerAdapter2, new PatternTopic("redisChatTest"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(Receiver2 receiver2) {
        return new MessageListenerAdapter(receiver2, "receiveMessage2");
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    Receiver2 receiver2() {
        return new Receiver2();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
