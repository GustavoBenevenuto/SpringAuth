package com.springauth.domain.products.entity;


import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "tb_products")
@Data
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = true)
	private String details;

	@CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

	@UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
	
}

