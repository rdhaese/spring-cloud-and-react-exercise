package com.rdhaese.springcloudandreactexercise.addressservice;

import com.rdhaese.springcloudandreactexercise.addressservice.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RibbonClient(name= "address-service", configuration = RibbonConfiguration.class)
@EnableCaching
public class AddressServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressServiceApplication.class, args);
    }
}
