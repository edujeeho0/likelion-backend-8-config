package com.example.article.repo;

import com.example.article.entity.Article;
import com.example.article.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {
    // 어떤 게시글(id=?)에 작성된 댓글들
    // SELECT * FROM comment WHERE article_id = ?
    List<Comment> findAllByArticleId(Long id);
    List<Comment> findAllByArticle(Article article);
}
