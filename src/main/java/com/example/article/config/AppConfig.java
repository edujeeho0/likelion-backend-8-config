package com.example.article.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    // 메서드의 결과로 반환되는 객체를 Bean 객체로 활용해주세요
    public AppConfigData configData() {
        return new AppConfigData("url", "apiKey");
    }
}
