package com.makenv.model.mc.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by wgy on 2017/3/9.
 */
@Configuration
@PropertySource("classpath:redisQueue.properties")
public class RedisQueueConfig {
    @Value("${redis.send.queue.name}")
    private String sendQueueName;
    @Value("${redis.send.queue.name}e")
    private String receiveQueueName;

    public String getSendQueueName() {
        return sendQueueName;
    }

    public String getReceiveQueueName() {
        return receiveQueueName;
    }

}
