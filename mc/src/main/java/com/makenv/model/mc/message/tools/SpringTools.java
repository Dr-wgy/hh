package com.makenv.model.mc.message.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringTools implements ApplicationContextAware {

    private ApplicationContext context = null;
    private SpringTools stools = null;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public  Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public   <T> T getBean(Class<T> clazz){

        return context.getBean(clazz);
    }
}