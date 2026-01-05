package com.shubhasmita.urlShortner.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "urls")
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "original_url", nullable = false)
	private String originalUrl;
	
	@Column(name = "short_code", nullable = false, unique = true)
	private String shortCode;
	
	@Column(name ="created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name ="expires_at")
	private LocalDateTime expiresAt;
	
	public Url(){
		
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
//	getters and setters
	
	public Long getId() {
		return id;
	}
	
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	
}
