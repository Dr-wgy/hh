package com.makenv.model.mc.message.redis;

import com.makenv.model.mc.constant.Constants;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.message.body.Message;
import com.makenv.model.mc.message.dispacher.AnnocationMessageDispacher;
import com.makenv.model.mc.message.dispacher.ImessageDispacher;
import com.makenv.model.mc.message.runable.MessageListenerRunable;
import com.makenv.model.mc.message.tools.SpingTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
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

        redisListenerThread.setDaemon(true);

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

                    Message message =  takeFromTailAndInsertTemQueue();

                    if (Objects.nonNull(message)){

                        ImessageDispacher imessageDispacher = SpingTools.getBean(AnnocationMessageDispacher.class);

                        RedisService redisService = SpingTools.getBean(RedisService.class);

                        MessageListenerRunable messageListenerRunable = new MessageListenerRunable(message,imessageDispacher,redisService);

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

    private Message takeFromTailAndInsertTemQueue() throws InterruptedException, IOException {

        lock.lockInterruptibly();

        try {

            String obj = redisService.brpop(Constants.REDIS_RECEIVE_QUEUE_NAME);

            Message message = JacksonUtil.jsonToObj(obj,Message.class);

            String tem_queue = String.join(":", Constants.TEMP_QUEUE_NAME_PREFIX ,message.getId());

            redisService.leftPush(tem_queue,obj);//插入临时队列

            if(Objects.isNull(obj)) {

                return null;
            }
            else {

                return message;
            }

        } finally {

            lock.unlock();
        }
    }
}
