package com.makenv.model.mc.message.dispacher;


import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.message.annoation.MessageInvoker;
import com.makenv.model.mc.message.body.Message;
import com.makenv.model.mc.message.exception.NoDispacherFoundException;
import com.makenv.model.mc.message.exception.NoUniqueInvokerExpection;
import com.makenv.model.mc.message.redis.RedisService;
import com.makenv.model.mc.message.tools.ClassPathPackageScanner;
import com.makenv.model.mc.message.tools.PackageScanner;
import com.makenv.model.mc.message.tools.SpingTools;
import com.makenv.model.mc.message.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wgy on 2017/2/20.
 */
@Component
public class AnnocationMessageDispacher implements ImessageDispacher {

    private static final String topPackageName = "com.makenv.model.mc.message.controller";

    @Autowired
    private RedisService redisService;

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

    @Override
    public boolean dispacher(Message message) {

        String uniqueLine = message.getType();

        if(handlerMappings.containsKey(uniqueLine)) {

            Method method = handlerMappings.get(uniqueLine);

            Class clazz = method.getDeclaringClass();

            Object body = message.getBody();

            Object obj = SpingTools.getBean(clazz);

            try {

                Class classes[] = method.getParameterTypes();

                //只支持一个参数的入口
                if(classes != null && classes.length != 0) {

                    if(String.class.equals(classes[0])) {

                        body = JacksonUtil.objToJson(body);

                    }

                    else if(body instanceof Map) {

                        Object temBody = classes[0].newInstance();

                        BeanUtil.transMap2Bean((Map)body,temBody);

                        body = temBody;

                    }
                }

                Object returnValue = method.invoke(obj,body);

            } catch (Exception e) {

                e.printStackTrace();

                return false;

            }
        }

        else {

            throw new NoDispacherFoundException("not dispacher found, please check your config");
        }

        return true;

    }
}
