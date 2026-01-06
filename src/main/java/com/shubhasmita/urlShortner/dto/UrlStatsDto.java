package com.shubhasmita.urlShortner.dto;

import java.time.LocalDateTime;

public class UrlStatsDto {
	
	private String shortCode;
	private String originalUrl;
	private Long clickCount;
	private LocalDateTime expiresAt;
	
	public UrlStatsDto(String shortCode,
            String originalUrl,
            Long clickCount,
            LocalDateTime expiresAt) {
			this.shortCode = shortCode;
			this.originalUrl = originalUrl;
			this.clickCount = clickCount;
			this.expiresAt = expiresAt;
			}
	
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	
	public Long getClickCount() {
		return clickCount;
	}
	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
	
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

}
