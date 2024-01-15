package com.example.article;

import com.example.article.dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

// Singleton Pattern
// 프로그램 전체에서 클래스의 구현체를 하나만 만들고 싶을 때
public class ArticleStoreSingleton {
    private final List<ArticleDto> articleDtoList = new ArrayList<>();
    private static ArticleStoreSingleton instance;
    private ArticleStoreSingleton() {}
    public static ArticleStoreSingleton getInstance() {
        if (instance == null)
            instance = new ArticleStoreSingleton();
        return instance;
    }
}
