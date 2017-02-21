package com.makenv.model.mc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alei on 2017/2/21.
 */
@EnableAutoConfiguration
@ComponentScan({"com.makenv.model.mc"})
@Configuration
public class Application {
  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);

  }
}
