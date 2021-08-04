package com.rdhaese.springcloudandreactexercise.studentservice;

import com.rdhaese.springcloudandreactexercise.studentservice.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RibbonClient(name = "student-service", configuration = RibbonConfiguration.class)
@EnableCircuitBreaker
public class StudentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }
}
