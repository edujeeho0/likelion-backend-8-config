package com.example.article;

import com.example.article.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
// 요청을 받는 클래스이다.
@Controller
@RequestMapping("article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private static final Logger logger
            = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping
    public String article(Model model) {
        log.debug("request to /article");
        model.addAttribute("articles", articleService.readAll());
        log.debug("before return index");
        return "index";
    }

    @GetMapping("write")
    public String articleWrite() {
        return "write";
    }

    @PostMapping
    public String articleCreate(
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("writer")
            String writer
    ) {
        Long newId = articleService.create(new ArticleDto(title, content, writer)).getId();
        return String.format("redirect:/article/%d", newId);
    }

    @GetMapping("{id}")
    public String articleOne(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.read(id));
        return "read";
    }


    @GetMapping("{id}/edit")
    public String articleEdit(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("article", articleService.read(id));
        return "edit";
    }

    @PostMapping("{id}/update")
    public String articleUpdate(
            @PathVariable("id")
            Long id,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content
    ) {
        articleService.update(id, new ArticleDto(title, content));
        return String.format("redirect:/article/%d", id);
    }

    @PostMapping("{id}/delete")
    public String articleDelete(
            @PathVariable("id")
            Long id
    ) {
        articleService.delete(id);
        return "redirect:/article";
    }
}
