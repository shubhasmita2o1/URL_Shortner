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
	
//	constructor Injection
	public UrlController (UrlServices urlServices) {
		this.urlServices = urlServices;
	}
	
//	create short url
	@PostMapping("/shorten")
	public ResponseEntity<String> shortenUrl(@RequestBody String OriginalUrl){
		Url url = urlServices.createShortUrl(OriginalUrl);
		
		String shortUrl = "http://localhost:8081/" + url.getShortCode();
		
		return ResponseEntity.ok(shortUrl);
	}
	
	@GetMapping("/{shortcode}")
	public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode){
		Optional<Url> urlOptional = urlServices.getOriginalUrl(shortCode);
		
		if(urlOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Url url = urlOptional.get();
		
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.location(URI.create(url.getOriginalUrl()))
				.build();
	}
	

}
