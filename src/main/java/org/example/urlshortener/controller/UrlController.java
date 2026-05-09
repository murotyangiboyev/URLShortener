package org.example.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.urlshortener.dto.UrlRequestDto;
import org.example.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "URL Shortener", description = "Shorten and redirect URLs")
public class UrlController {
    private final UrlService urlService;

    @Operation(summary = "Shorten a URL", description = "Takes a long URL and returns a short code")
    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody UrlRequestDto dto) {
        String shortcode = urlService.shortenUrl(dto.getUrl());
        return ResponseEntity.ok("/api/url/" + shortcode);
    }

    @Operation(summary = "Shorten a URL", description = "Takes a long URL and returns a short code")
    @GetMapping("/url/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

}
