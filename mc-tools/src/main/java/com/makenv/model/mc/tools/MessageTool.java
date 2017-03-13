package com.makenv.model.mc.tools;

import com.makenv.model.mc.core.util.FileUtil;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;

/**
 * Created by alei on 2017/3/9.
 */
public class MessageTool {
  public static void main(String[] args) throws IOException {
    String queue = "receive_queue_name";
    Jedis jedis = new Jedis("166.111.42.46", 16379);
    jedis.auth("123456");
//    String content = FileUtil.readLocalFile(new File("D:\\work\\mc\\samples\\api\\BM\\test\\start-model-2.json"));
    String content = FileUtil.readLocalFile(new File("D:\\work\\mc\\samples\\api\\BM\\test\\create-domain-1.json"));
    jedis.lpush(queue, content);
  }
}
