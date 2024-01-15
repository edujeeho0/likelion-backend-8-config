package com.example.article.repo;

import com.example.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository
        extends JpaRepository<Article, Long> {
    // SELECT * FROM article ORDER BY id DESC;
    List<Article> findAllByOrderByIdDesc();

    // SELECT * FROM article ORDER BY writer DESC;
    List<Article> findAllByOrderByWriterDesc();

    // SELECT * FROM article ORDER BY title;
    List<Article> findAllByOrderByTitle();

    // 나보다 나중에 작성되었으며
    // SELECT * FROM article WHERE id > ?;
    List<Article> findAllByIdGreaterThan(Long id);
    // 나와 같거나 나보다 먼저 작성된 글
    // SELECT * FROM article WHERE id <= ?;
    List<Article> findAllByIdLessThanEqual(Long id);

    // SELECT * FROM article WHERE id < ? ORDER BY id DESC;
    List<Article> findAllByIdLessThanOrderByIdDesc(Long id);

    // 제목에 'Lorem'이라는 문구가 포함된 글의 목록을 조회
    // SELECT * FROM article WHERE title LIKE '%Lorem%';
    List<Article> findAllByTitleContaining(String title);

    // SELECT * FROM article WHERE id > ? LIMIT 1;
    Optional<Article> findFirstByIdAfter(Long id);
    // SELECT * FROM article WHERE id < ? ORDER BY id DESC
    // LIMIT 1;
    Optional<Article> findFirstByIdLessThanOrderByIdDesc(Long id);
}
// save - CREATE
// findById - PK를 기준으로 READ
// findAll - 전체 READ
// deleteById - PK를 기준으로 DELETE
// delete - Entity를 기준으로 DELETE

/*
SELECT * FROM articles;
SELECT * FROM articles ORDER BY id DESC;
 */

