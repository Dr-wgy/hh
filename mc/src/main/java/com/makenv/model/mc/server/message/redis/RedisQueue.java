package com.makenv.model.mc.server.message.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.makenv.model.mc.core.config.RedisQueueConfig;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.config.Cmd;
import com.makenv.model.mc.server.constant.Constants;
import com.makenv.model.mc.server.message.body.Message;
import com.makenv.model.mc.server.message.body.MessageWrapper;
import com.makenv.model.mc.server.message.dispacher.AnnocationMessageDispacher;
import com.makenv.model.mc.server.message.runable.MessageListenerRunable;
import com.makenv.model.mc.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    private Logger logger = LoggerFactory.getLogger(RedisQueue.class);


    @Autowired
    RedisQueueConfig redisQueueConfig;

    @Autowired
    private RedisService redisService;

    @Autowired
    private Cmd cmd;

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

    public void sendMessgae(Message messgae) throws JsonProcessingException {

        redisService.leftPush(redisQueueConfig.getSendQueueName(), JacksonUtil.objToJson(messgae));
    }

    private MessageWrapper takeFromTailAndInsertTemQueue() throws InterruptedException, IOException {

        try {

            MessageWrapper messageWrapper = new MessageWrapper();

            String obj = redisService.brpop(redisQueueConfig.getReceiveQueueName());

            logger.info(obj);

            Message message = messageCheck(obj);

            String queue_name = String.join(":",Constants.TEMP_QUEUE_NAME_PREFIX,message.getId());

            redisService.set(queue_name,obj);

            messageWrapper.setTempQueueName(queue_name);

            messageWrapper.setMessage(message);

            if(Objects.isNull(obj)) {

                return null;
            }

            else {

                return messageWrapper;
            }

        } finally {

        }
    }

    //消息检查
    private Message messageCheck(String messageStr){

        Message message = null;

        try {

            message = JacksonUtil.jsonToObj(messageStr,Message.class);

            if(StringUtil.isEmpty(message.getId()) || StringUtil.isEmpty(message.getType()) || Objects.isNull(message.getBody())) {

                logger.info("消息格式不能为空");

                logger.info(messageStr);

                message = null;

                FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

                //记录日志

            }
        }

        catch (InvalidFormatException e) {

            logger.info("时间格式不正确");

            logger.info(messageStr);

            e.printStackTrace();

            FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

            //记录日志

        }

        catch (IOException e) {

            logger.info("消息格式不正确");

            logger.info(messageStr);

            e.printStackTrace();

            FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

            //记录日志
        }

        return message;
    }
}
