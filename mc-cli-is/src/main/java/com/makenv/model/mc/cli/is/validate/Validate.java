package com.makenv.model.mc.cli.is.validate;

/**
 * Created by wgy on 2017/3/22.
 */
@FunctionalInterface
public interface Validate {

    boolean matches(String source,String target);
}
