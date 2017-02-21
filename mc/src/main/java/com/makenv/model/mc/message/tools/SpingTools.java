package com.makenv.model.mc.message.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpingTools implements ApplicationContextAware {

    private static ApplicationContext context = null;
    private static SpingTools stools = null;

    public synchronized static SpingTools init() {
        if (stools == null) {
            stools = new SpingTools();
        }
        return stools;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public synchronized  static  <T> T getBean(Class<T> clazz){

        return context.getBean(clazz);
    }
}