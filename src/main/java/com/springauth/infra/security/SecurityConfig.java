package com.springauth.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Classes com @Configuration podem definir beans que serão gerenciados pelo Spring.
@EnableWebSecurity // Habilita a segurança web do Spring Security na aplicação. Permite customizar a segurança via HttpSecurity, filtros, etc.
public class SecurityConfig {
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Bean // Define a cadeia de filtros de segurança (SecurityFilterChain).
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable()) // Desativa proteção CSRF (útil em APIs REST que usam JWT, não sessões).
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define política de sessão como STATELESS (sem manter sessão no servidor), porque usamos JWT, que já transporta a autenticação.
				.authorizeHttpRequests(auhtorize -> auhtorize
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Permite que qualquer usuário acesse o endpoint de login sem autenticação
						.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()//.hasRole("ADMIN") // Permite que apenas usuários com papel "ADMIN" possam registrar novos usuários
						.anyRequest().authenticated() // Todas as outras requisições precisam estar autenticadas
					)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o SecurityFilter personalizado antes do filtro padrão de autenticação. O SecurityFilter verifica o token JWT e autentica o usuário
				.build(); // Constrói a cadeia de filtros de segurança
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager(); // Define o AuthenticationManager que será usado para autenticar usuários. Ex.: no login, para validar usuário e senha
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Define o codificador de senha. BCrypt é seguro e recomendado para hashing de senhas
	}
}
