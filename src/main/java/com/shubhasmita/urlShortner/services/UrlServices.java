package com.shubhasmita.urlShortner.services;

import com.shubhasmita.urlShortner.entity.Url;
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
		
		return urlRepository.save(url);
		
	}
	
//	get original url using short code
	public Optional<Url> getOriginalUrl(String shortCode){
		Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);
		
		if(urlOptional.isPresent()) {
			Url url = urlOptional.get();
			
//			check expire
			if(url.getExpiresAt() != null &&
					url.getExpiresAt().isBefore(LocalDateTime.now())) {
				return Optional.empty();
			}
		}
		return urlOptional;
	}
	
//	generate short code
	private String generateShortCode(){
		return UUID.randomUUID()
					.toString()
					.substring(0,6);
	}

}
