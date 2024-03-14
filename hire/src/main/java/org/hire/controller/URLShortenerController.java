package org.hire.controller;


import lombok.RequiredArgsConstructor;
import org.hire.domain.dto.URLShortenerDto;
import org.hire.domain.entity.URLShortenerEntity;
import org.hire.service.URLShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class URLShortenerController {

    private final URLShortenerService service;

    @PutMapping("/create")
    public ResponseEntity<URLShortenerDto> shortenUrl (@RequestParam(required = false) String alias, @RequestParam String url){
        return ResponseEntity.ok(service.encurtarUrl(alias, url));
    }

    @GetMapping("/{alias}")
    public ResponseEntity<String> redirect(@PathVariable String alias) {
        URLShortenerEntity entity = service.getOriginalURL(alias);

        if (Objects.isNull(entity.getUrl())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"ERR_CODE\": \"002\", \"Description\": \"SHORTENED URL NOT FOUND\"}");
        }
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", entity.getUrl()).build();
    }

    @GetMapping("/top-urls")
    public ResponseEntity<List<URLShortenerEntity>> getTop10MostAccessedUrls() {
        return ResponseEntity.ok(service.findTop10MostAccessedUrls());
    }
}
