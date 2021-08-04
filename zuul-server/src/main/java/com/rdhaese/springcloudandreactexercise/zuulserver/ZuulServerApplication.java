package com.rdhaese.springcloudandreactexercise.zuulserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }

    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Value("#{'${allowedOriginList}'.split(',')}")
        private String[] allowedOriginList;

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins(allowedOriginList)
                    .allowedMethods("*")
                    .allowedHeaders( "authorization")
                    .allowCredentials(true)
                    .exposedHeaders("Cache-Control", "Content-Type");

        }
    }
}
