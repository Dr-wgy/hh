package com.makenv.model.mc.server.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by wgy on 2017/3/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface ApplicationProperties {

    String[] locations();

    String prefix() default "";

}
