package com.shubhasmita.urlShortner.services;

import com.shubhasmita.urlShortner.entity.Url;
import com.shubhasmita.urlShortner.exception.UrlNotFoundException;
import com.shubhasmita.urlShortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlServices {
	
	private final UrlRepository urlRepository;
	
//	constructor Injection
	public UrlServices(UrlRepository urlRepository) {
		this.urlRepository = urlRepository;
	}
	
//	create short URL
	public Url createShortUrl(String originalUrl) {
		
		Url url = new Url();
		url.setOriginalUrl(originalUrl);
		
//		generate unique short code
		String shortCode = generateShortCode();
		url.setShortCode(shortCode);
		
//		expiration days
		url.setExpiresAt(LocalDateTime.now().plusDays(15));
		
//		click count
		url.setClickCount(0L);
		
		return urlRepository.save(url);
		
	}
	
//	stats
	
	public Url getUrlForStats(String shortCode) {

	    Url url = urlRepository.findByShortCode(shortCode)
	            .orElseThrow(() ->
	                    new UrlNotFoundException("Short URL not found"));

	    if (url.getExpiresAt() != null &&
	        url.getExpiresAt().isBefore(LocalDateTime.now())) {
	        throw new UrlNotFoundException("Short URL has expired");
	    }

	    return url;
	}

	
	
//	public Optional<Url> getUrlForStats(String shortCode) {
//
//	    Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);
//
//	    if (urlOptional.isEmpty()) {
//	        return Optional.empty();
//	    }
//
//	    Url url = urlOptional.get();
//
//	    if (url.getExpiresAt() != null &&
//	        url.getExpiresAt().isBefore(LocalDateTime.now())) {
//	        return Optional.empty();
//	    }
//
//	    return Optional.of(url);
//	}

	
//	get original url using short code
	
	public Url getOriginalUrl(String shortCode) {

	    Url url = urlRepository.findByShortCode(shortCode)
	            .orElseThrow(() ->
	                    new UrlNotFoundException("Short URL not found"));

	    if (url.getExpiresAt() != null &&
	        url.getExpiresAt().isBefore(LocalDateTime.now())) {
	        throw new UrlNotFoundException("Short URL has expired");
	    }

	    url.setClickCount(url.getClickCount() + 1);
	    urlRepository.save(url);

	    return url;
	}

	
//	public Optional<Url> getOriginalUrl(String shortCode){
//		Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);
//		
//		if(urlOptional.isPresent()) {
//			Url url = urlOptional.get();
//			
////			check expire
//			if(url.getExpiresAt() != null &&
//					url.getExpiresAt().isBefore(LocalDateTime.now())) {
//				return Optional.empty();
//			}
//		}
//		
//		return urlOptional;
//	}
	
//	public Optional<Url> getOriginalUrl(String shortCode) {
//
//	    Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);
//
//	    if (urlOptional.isEmpty()) {
//	        return Optional.empty();
//	    }
//
//	    Url url = urlOptional.get();
//
//	    
//	    if (url.getExpiresAt() != null &&
//	        url.getExpiresAt().isBefore(LocalDateTime.now())) {
//	        return Optional.empty();
//	    }
//
//	    // valid redirect → increment click count
//	    url.setClickCount(url.getClickCount() + 1);
//	    urlRepository.save(url);
//
//	    return Optional.of(url);
//	}

	
//	generate short code
	private String generateShortCode(){
		return UUID.randomUUID()
					.toString()
					.substring(0,6);
	}

}
