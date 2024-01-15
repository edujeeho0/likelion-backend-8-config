package com.example.article;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RootController {
    /*private static final Logger log
            = LoggerFactory.getLogger(RootController.class);*/

    @GetMapping
    public String root() {
        return "redirect:/article";
    }

    @GetMapping("/log")
    public String logTest() {
        log.trace("TRACE message");
        log.debug("DEBUG message");
        log.info("INFO message");
        log.warn("WARN message");
        log.error("ERROR message");
        return "log";
    }
}
