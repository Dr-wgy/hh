package com.makenv.model.mc.cli.helper;

import com.makenv.model.mc.core.config.RedisQueueConfig;
import com.makenv.model.mc.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by alei on 2017/2/28.
 */
@Component
public class JedisHelper {
  @Autowired
  private RedisService redisService;

  @Autowired
  private RedisQueueConfig redisQueueConfig;

  public void sendMessage(String content) {

    redisService.leftPush(redisQueueConfig.getSendQueueName(), content);

  }

}
