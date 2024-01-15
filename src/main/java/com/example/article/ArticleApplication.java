package com.example.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class ArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
//		ArticleStoreSingleton store = new ArticleStoreSingleton();
		ArticleStoreSingleton store = ArticleStoreSingleton.getInstance();
		// 0.5초마다 로그를 남기는 코드
		/*Runnable logRunnable = () -> log.info("log test {}", LocalDateTime.now());
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(logRunnable, 0, 500, TimeUnit.MILLISECONDS);*/
	}

}
