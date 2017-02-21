package com.makenv.model.mc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alei on 2017/2/21.
 */
@SpringBootApplication
// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {
  public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
  }
}
