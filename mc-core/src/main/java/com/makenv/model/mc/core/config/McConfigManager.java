package com.makenv.model.mc.core.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wgy on 2017/2/27.
 */
@Configuration
public class McConfigManager implements InitializingBean {

    private String instanceConfigPath = "file:etc/instance.conf";

    private String systemRouterPath = "file:etc/%s/system.conf";

    private String systemPath = "file:etc/system.conf";

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    private SystemConfig systemConfig;

    @Override
    public void afterPropertiesSet() throws Exception {

        Config routerConfig = getConfigPath(instanceConfigPath);

        String nodeMachineConfig = routerConfig.getString("system.instance");

        Config nodeMachineSystemConfig = getConfigPath(String.format(systemRouterPath, nodeMachineConfig));

        Config basicSystemConfig = getConfigPath(systemPath);

        Config dealConfig = nodeMachineSystemConfig.
                withFallback(basicSystemConfig).withFallback(routerConfig).resolve().getConfig("system");

        systemConfig = ConfigBeanFactory.create(dealConfig,SystemConfig.class);


    }

    private Config getConfigPath(String configPath) {

        Config config = null;

        if (configPath.startsWith("classpath:")) {
            if (configPath.length() < 11) {
                throw new IllegalArgumentException("configPath seems not right");
            }
            try (InputStream in = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(configPath.substring(10))) {
                config = ConfigFactory.parseReader(new InputStreamReader(in));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            if (configPath.startsWith("file:")) {
                configPath = configPath.substring(5);
            }
            File configFile = new File(configPath);
            if (!configFile.exists() || configFile.isDirectory()) {
                configFile = new File("../" + configPath);
                if (!configFile.exists() || configFile.isDirectory()) {
                    throw new IllegalArgumentException(
                            "Config file doesn't exist or it's directory");
                }
            }
            config = ConfigFactory.parseFile(configFile);
        }

        return config;
    }

}
