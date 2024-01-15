package com.example.article;

import com.example.article.config.AppConfigData;
import org.springframework.stereotype.Component;

// 가장 기초적인 Bean Annotation
@Component
public class AppComponent {
    private final AppConfigData configData;
    public AppComponent(AppConfigData configData) {
        this.configData = configData;
    }
}
