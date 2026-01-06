package com.shubhasmita.urlShortner.controller;


import com.shubhasmita.urlShortner.dto.UrlRequestDto;
import com.shubhasmita.urlShortner.dto.UrlResponseDto;
import com.shubhasmita.urlShortner.dto.UrlStatsDto;
import com.shubhasmita.urlShortner.entity.Url;
import com.shubhasmita.urlShortner.services.UrlServices;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
//import java.util.Optional;

@RestController
public class UrlController {

    private final UrlServices urlServices;

    // Constructor Injection
    public UrlController(UrlServices urlServices) {
        this.urlServices = urlServices;
    }

    // Create short URL
//    @PostMapping("/shorten")
//    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
//
//        Url url = urlServices.createShortUrl(originalUrl);
//        String shortUrl = "http://localhost:8081/" + url.getShortCode();
//
//        return ResponseEntity.ok(shortUrl);
//    }
    
     @PostMapping("/shorten")
     public ResponseEntity<UrlResponseDto> shortenUrl(
    		@Valid @RequestBody UrlRequestDto requestDto){
    	 
    	 Url url = urlServices.createShortUrl(requestDto.getOriginalUrl());
    	 
    	 String shortUrl = "http://localhost:8081/" + url.getShortCode();
    	 
    	 return ResponseEntity.ok(new UrlResponseDto(shortUrl));
     }

    // Redirect to original URL
    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirectToOriginal(
            @PathVariable("shortcode") String shortCode) {

//        Optional<Url> urlOptional = urlServices.getOriginalUrl(shortCode);
//
//        if (urlOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }

        Url url = urlServices.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
    
    
    
    @GetMapping("/stats/{shortcode}")
    public ResponseEntity<UrlStatsDto> getStats(
            @PathVariable String shortcode) {

        Url url = urlServices.getUrlForStats(shortcode);

        UrlStatsDto stats = new UrlStatsDto(
                url.getShortCode(),
                url.getOriginalUrl(),
                url.getClickCount(),
                url.getExpiresAt()
        );

        return ResponseEntity.ok(stats);
    }


}

