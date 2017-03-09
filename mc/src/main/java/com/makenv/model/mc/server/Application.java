package com.makenv.model.mc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alei on 2017/2/21.
 */
@EnableAutoConfiguration
@ComponentScan({"com.makenv.model.mc.server","com.makenv.model.mc.redis","com.makenv.model.mc.core"})
@Configuration
public class Application {
  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);

  }
}
