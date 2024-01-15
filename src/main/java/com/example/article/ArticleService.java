package com.example.article;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.Article;
import com.example.article.repo.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
// 비즈니스 로직을 담당하는 클래스이다.
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleDto create(ArticleDto dto) {
        Article article = new Article(
                dto.getTitle(), dto.getContent(), dto.getWriter()
        );
        return ArticleDto.fromEntity(repository.save(article));
    }

    public List<ArticleDto> readAll() {
        log.debug("call readAll()");
        List<ArticleDto> articleList = new ArrayList<>();
        for (Article article: repository.findAll()) {
            log.trace(article.toString());
            articleList.add(ArticleDto.fromEntity(article));
        }
        log.debug(articleList.toString());

        return articleList;
        // stream
        /*return repository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());*/
    }

    public ArticleDto read(Long id) {
//        Article article = repository.findById(id).orElseThrow();
        Optional<Article> optionalArticle = repository.findById(id);
        // 만약 id에 해당하는 article이 없다
        if (optionalArticle.isEmpty()) {
            log.warn(String.format("article with id: %d not present", id));
            optionalArticle.orElseThrow();
        }
        Article article = optionalArticle.get();
        return ArticleDto.fromEntity(article);
    }

    public ArticleDto update(Long id, ArticleDto dto) {
        Article article = repository.findById(id).orElseThrow();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return ArticleDto.fromEntity(repository.save(article));
    }

    public void delete(Long id) {
        repository.delete(repository.findById(id).orElseThrow());
    }
}
