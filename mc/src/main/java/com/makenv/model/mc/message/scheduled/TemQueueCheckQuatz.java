package com.makenv.model.mc.message.scheduled;

import com.makenv.model.mc.message.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * Created by wgy on 2017/2/21.
 */
@Configuration
@EnableScheduling
@PropertySource("classpath:check-queue.properties")// 启用定时任务
public class TemQueueCheckQuatz {

    @Autowired
    private RedisService redisSevice;


    //对未处理的消息进行处理(临时队列)
    @Scheduled(cron = "${redis.temp.queue.check.time}")
    public void TemQueueCheck(){

        System.out.println(LocalDateTime.now());
    }
}
