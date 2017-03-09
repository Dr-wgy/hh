package com.makenv.model.mc.server.message.dispacher;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.makenv.model.mc.server.constant.Constants;
import com.makenv.model.mc.server.message.annoation.MessageInvoker;
import com.makenv.model.mc.server.message.body.Message;
import com.makenv.model.mc.server.message.exception.NoUniqueInvokerExpection;
import com.makenv.model.mc.server.message.tools.ClassPathPackageScanner;
import com.makenv.model.mc.server.message.tools.SpringTools;
import com.makenv.model.mc.server.message.util.BeanUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.tools.PackageScanner;
import com.makenv.model.mc.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wgy on 2017/2/20.
 */
@Component
public class AnnocationMessageDispacher implements ImessageDispacher {

    private Logger logger = LoggerFactory.getLogger(AnnocationMessageDispacher.class);


    private static final String topPackageName = "com.makenv.model.mc.server.message.controller";

    @Autowired
    private RedisService redisService;

    @Autowired
    private SpringTools spingTools;

    private Map<String,Method> handlerMappings;

    private PackageScanner packagerScanner;


    @PostConstruct
    public void init(){

        packagerScanner =  new ClassPathPackageScanner(topPackageName,MessageInvoker.class);

        handlerMappings = new LinkedHashMap<String,Method>();

        List<String> fullyQualifiedClassNameList = null;

        try {

            fullyQualifiedClassNameList = packagerScanner.getFullyQualifiedClassNameList();

            for(String fullyQualifiedClassName:fullyQualifiedClassNameList) {

                try {

                    Class clazz = Class.forName(fullyQualifiedClassName);

                    MessageInvoker classMessageInvoker = (MessageInvoker) clazz.getDeclaredAnnotation(MessageInvoker.class);

                    String classValue = classMessageInvoker.value();

                    Method[] methods = clazz.getDeclaredMethods();

                    for(Method eachMethod : methods) {

                        MessageInvoker messageInvoker = eachMethod.getDeclaredAnnotation(MessageInvoker.class);

                        String methodValue = messageInvoker.value();

                        String uniqueFlag = String.join("",classValue,methodValue);

                        if(handlerMappings.containsKey(uniqueFlag)) {

                             throw new NoUniqueInvokerExpection("not a unique flag,please check annotation messageInvoker's value ");
                        }
                        else {

                            handlerMappings.put(uniqueFlag,eachMethod);
                        }

                    }

                } catch (ClassNotFoundException e) {

                    e.printStackTrace();

                    continue;
                }

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    //消息检查
    private Message messageCheck(String messageStr){

        Message message = null;

        try {

            message = JacksonUtil.jsonToObj(messageStr,Message.class);

            if(StringUtil.isEmpty(message.getId()) || StringUtil.isEmpty(message.getType()) || Objects.isNull(message.getBody())) {

                logger.info("消息格式不能为空");

                message = null;

                FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

                //记录日志

            }
        }

        catch (InvalidFormatException e) {

            logger.info("时间格式不正确");

            e.printStackTrace();

            FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

            //记录日志


        }

        catch (IOException e) {

            logger.info("消息格式不正确");

            e.printStackTrace();

            FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

            //记录日志
        }

        return message;
    }

    @Override
    public boolean dispacher(String messageStr) {

        Message message = messageCheck(messageStr);

        if(message != null) {

            String uniqueLine = message.getType();

            if(handlerMappings.containsKey(uniqueLine)) {

                Method method = handlerMappings.get(uniqueLine);

                Class clazz = method.getDeclaringClass();

                Object body = message.getBody();

                Object obj = spingTools.getBean(clazz);

                try {

                    Class classes[] = method.getParameterTypes();

                    //只支持一个参数的入口
                    if(classes != null && classes.length != 0) {

                        if(String.class.equals(classes[0])) {

                            body = JacksonUtil.objToJson(body);

                        }

                        else if(body instanceof Map) {

                            Object temBody = classes[0].newInstance();

                            body = BeanUtil.transMap2Bean((Map)body,temBody);

                        }
                    }

                    Object returnValue = method.invoke(obj,body);

                    if( returnValue instanceof Boolean) {

                        return (Boolean) returnValue;
                    }

                    else {

                        return false;
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                    return false;

                }
            }

            else {

                logger.info("not dispacher found, please check your config");
                //记录日志
                FileUtil.writeLogByDaily(Constants.ERROR_MSG_LOG_PREFIX,messageStr);

                //throw new NoDispacherFoundException("not dispacher found, please check your config");

                return false;
            }

        }

        else {

            return false;
        }
    }
}
