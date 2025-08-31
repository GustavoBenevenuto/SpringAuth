package com.springauth.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springauth.domain.users.entity.User;

@Service
public class TokenService {
	
	// Injeta o valor do segredo do token definido no arquivo application.properties
	@Value("${api.security.token.secret}")
	private String secret;
	
	// Método para gerar um token JWT para o usuário informado
	public String generateToken(User user) {
		try {
			// Define o algoritmo de assinatura HMAC256 usando o segredo configurado
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("SpringAuth") // Define quem emitiu o token
					.withSubject(user.getEmail()) // Define o assunto do token, aqui o e-mail do usuário
					.withExpiresAt(generateExpirationDate()) // Define a data de expiração do token
					.sign(algorithm); // Assina o token com o algoritmo definido
			return token;
		} catch (JWTCreationException jwtEx) {
			// Captura erros na criação do token
			throw new RuntimeException("Error while generatin token", jwtEx);
		}
	}
	
	// Método para validar um token JWT e retornar o assunto (usuário) se for válido
	public String validateToken(String token) {
		try {
			// Define novamente o algoritmo HMAC256 com o mesmo segredo
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer("SpringAuth") // Confirma que o token foi emitido por "SpringAuth"
					.build()
					.verify(token) // Verifica a validade do token
					.getSubject(); // Retorna o assunto do token, que é o e-mail do usuário
		} catch (JWTVerificationException jwtEx) {
			// Captura erros de verificação do token (inválido, expirado, etc.)
			return ""; // Retorna string vazia se o token não for válido
		}
	}
	
	// Método privado para gerar a data de expiração do token
	private Instant generateExpirationDate() {
		return LocalDateTime.now() // Pega o horário atual
				.plusHours(2) // Adiciona 2 horas ao horário atual
				.toInstant(ZoneOffset.of("-03:00")); // Converte para Instant considerando o fuso horário -03:00
	}
}
