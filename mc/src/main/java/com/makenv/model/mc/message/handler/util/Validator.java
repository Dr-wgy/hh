package com.makenv.model.mc.message.handler.util;

/**
 * Created by wgy on 2017/2/24.
 */
@FunctionalInterface
public interface Validator {

    boolean validate(Object flag);

}
