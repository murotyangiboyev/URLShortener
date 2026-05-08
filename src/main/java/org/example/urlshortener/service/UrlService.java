package org.example.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.example.urlshortener.model.Url;
import org.example.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String shortenUrl(String originalUrl){
        String shortCode = generateCode(originalUrl);

        while (urlRepository.existsByShortCode(shortCode)){
            shortCode = generateCode(originalUrl + System.nanoTime());
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);

        return shortCode;
    }

    private String generateCode(String input){
        int hash = Math.abs(input.hashCode());
        StringBuilder code = new StringBuilder();
        while (hash > 0 && code.length() < 6){
            code.append(BASE62.charAt(hash % BASE62.length()));
            hash /= BASE62.length();
        }
        return code.toString();
    }

    public String getOriginalUrl(String shortCode){
        return urlRepository.findByShortCode(shortCode)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }
}
