package com.makenv.model.mc.message.redis;

import com.makenv.model.mc.constant.Constants;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.message.body.Message;
import com.makenv.model.mc.message.body.MessageWrapper;
import com.makenv.model.mc.message.dispacher.AnnocationMessageDispacher;
import com.makenv.model.mc.message.runable.MessageListenerRunable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private Lock lock = new ReentrantLock();

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

        if(threadPoolExecutor.getActiveCount() >= threadPoolExecutor.getMaximumPoolSize()) {

            return false;

        }

        return true;
    }

    private MessageWrapper takeFromTailAndInsertTemQueue() throws InterruptedException, IOException {

        lock.lockInterruptibly();

        try {

            MessageWrapper messageWrapper = new MessageWrapper();

            String obj = redisService.brpop(Constants.REDIS_RECEIVE_QUEUE_NAME);

            String queue_name = String.join(":",Constants.TEMP_QUEUE_NAME_PREFIX,UUID.randomUUID().toString());

            redisService.set(queue_name,obj);

            Message message = JacksonUtil.jsonToObj(obj,Message.class);

            messageWrapper.setTempQueueName(queue_name);

            messageWrapper.setMessage(message);

            if(Objects.isNull(obj)) {

                return null;
            }

            else {

                return messageWrapper;
            }

        } finally {

            lock.unlock();
        }
    }
}
