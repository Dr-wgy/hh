package com.makenv.model.mc.cli.helper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by alei on 2017/2/28.
 */
public class JedisHelper {
  private final static JedisPool POOL;

  static {
    POOL = new JedisPool(new JedisPoolConfig(), "166.111.42.46", 16379, 2000, "123456");
  }

  public static void sendMessage(String content) {
    Jedis jedis = POOL.getResource();
    jedis.lpush("xxx", content);
  }
}
