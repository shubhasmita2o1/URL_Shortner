package com.shubhasmita.urlShortner.controller;

import com.shubhasmita.urlShortner.entity.Url;
import com.shubhasmita.urlShortner.services.UrlServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlServices urlServices;

    // Constructor Injection
    public UrlController(UrlServices urlServices) {
        this.urlServices = urlServices;
    }

    // Create short URL
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {

        Url url = urlServices.createShortUrl(originalUrl);
        String shortUrl = "http://localhost:8081/" + url.getShortCode();

        return ResponseEntity.ok(shortUrl);
    }

    // Redirect to original URL
    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirectToOriginal(
            @PathVariable("shortcode") String shortCode) {

        Optional<Url> urlOptional = urlServices.getOriginalUrl(shortCode);

        if (urlOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Url url = urlOptional.get();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
}

