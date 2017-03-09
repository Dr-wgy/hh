package com.makenv.model.mc.server.message.redis;

import com.makenv.model.mc.server.constant.Constants;
import com.makenv.model.mc.server.message.body.MessageWrapper;
import com.makenv.model.mc.server.message.dispacher.AnnocationMessageDispacher;
import com.makenv.model.mc.server.message.runable.MessageListenerRunable;
import com.makenv.model.mc.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wgy on 2017/2/20.
 */
@Component
public class RedisQueue{

    @Autowired
    private RedisService redisService;

    @Autowired
    private AnnocationMessageDispacher annocationMessageDispacher;


    private ThreadPoolExecutor threadPoolExecutor;

    private RedisListenerThread redisListenerThread;

/*
    private Lock lock = new ReentrantLock();
*/

    @PostConstruct
    public void init() {

        threadPoolExecutor = new ThreadPoolExecutor(1000, 1000,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());

        if(null == redisListenerThread) {

            redisListenerThread = new RedisListenerThread();

        }

        //redisListenerThread.setDaemon(true);

        redisListenerThread.start();

    }

    class RedisListenerThread extends Thread {

        @Override
        public void run() {

            while(true) {

                try {

                    if(checkIsBusyOrNot()) {

                        TimeUnit.SECONDS.sleep(2);

                        continue;
                    }

                    MessageWrapper messageWrapper =  takeFromTailAndInsertTemQueue();

                    if (Objects.nonNull(messageWrapper) && Objects.nonNull(messageWrapper.getMessage())){

                        MessageListenerRunable messageListenerRunable = new MessageListenerRunable(messageWrapper,annocationMessageDispacher,redisService);

                        threadPoolExecutor.execute(messageListenerRunable);

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }
    }

    private boolean checkIsBusyOrNot(){

        //检查当前服务线程是否繁忙，留下两个线程作为缓冲

        if(threadPoolExecutor.getActiveCount() >= threadPoolExecutor.getMaximumPoolSize() - 2 ) {

            return true;

        }

        return false;
    }

    private MessageWrapper takeFromTailAndInsertTemQueue() throws InterruptedException, IOException {

        /*lock.lockInterruptibly();*/

        try {

            MessageWrapper messageWrapper = new MessageWrapper();

            String obj = redisService.brpop(Constants.REDIS_RECEIVE_QUEUE_NAME);

            String queue_name = String.join(":",Constants.TEMP_QUEUE_NAME_PREFIX,UUID.randomUUID().toString());

            redisService.set(queue_name,obj);

            messageWrapper.setTempQueueName(queue_name);

            messageWrapper.setMessage(obj);

            if(Objects.isNull(obj)) {

                return null;
            }

            else {

                return messageWrapper;
            }

        } finally {

            /*lock.unlock();*/
        }
    }
}
