package com.springauth.controller.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springauth.domain.products.entity.Product;
import com.springauth.domain.products.repository.ProductRepository;
import com.springauth.domain.users.entity.User;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductRepository repository;
	
	@GetMapping
	public List<Product> getAll(@AuthenticationPrincipal User user) {
		return repository.findAll();
	}
}
