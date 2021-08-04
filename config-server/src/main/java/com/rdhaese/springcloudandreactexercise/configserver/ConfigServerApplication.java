package com.rdhaese.springcloudandreactexercise.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] arguments) {
        SpringApplication.run(ConfigServerApplication.class, arguments);
    }
}
