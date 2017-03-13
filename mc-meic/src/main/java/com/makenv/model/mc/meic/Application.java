package com.makenv.model.mc.meic;

import com.makenv.model.mc.meic.request.AnstractRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static Logger logger = LoggerFactory.getLogger(AnstractRequest.class);

  public static void main(String[] args) throws Exception {

        if(args.length < 0) {

            logger.info("please check your cmd command,confirm you input confFilePath");

            System.exit(1);

        }

        else {

            //配置文件的路径
            String configPath = args[0];

            MeicUrlTools meicUrlTools = new MeicUrlTools(configPath);

            meicUrlTools.doMeicJob();

        }
  }
}