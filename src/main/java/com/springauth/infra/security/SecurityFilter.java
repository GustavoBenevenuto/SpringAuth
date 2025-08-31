package com.springauth.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springauth.domain.users.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserService userService;
	
	// Este método é executado a cada requisição HTTP recebida
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Recupera o token do cabeçalho "Authorization"
		var token = this.recoverToken(request);
		
		if(token != null) {
			// Valida o token e extrai o "subject" (neste caso, o e-mail do usuário)
			var subjectEmail = tokenService.validateToken(token);
			
			// Busca os dados do usuário no banco a partir do e-mail
			UserDetails user = userService.getUserByEmail(subjectEmail);
			
			// Cria um objeto de autenticação com o usuário e suas permissões (authorities)
			var authentication  = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
			// Coloca a autenticação no contexto de segurança do Spring
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		// Continua o fluxo da requisição (outros filtros e controllers)
		filterChain.doFilter(request, response);
	}
	
	// Método auxiliar para recuperar o token JWT do cabeçalho da requisição
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}

}
