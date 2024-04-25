package com.example.project1.config;

import com.example.project1.aop.LoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ProjectConfig {
    @Bean
    LoggingAspect loggingAspect(){
        return new LoggingAspect();
    }
}
