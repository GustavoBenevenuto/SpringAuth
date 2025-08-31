package com.springauth.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springauth.controller.auth.dto.AuthDTO;
import com.springauth.controller.auth.dto.LoginResponseDTO;
import com.springauth.controller.auth.dto.RegisterDTO;
import com.springauth.domain.users.entity.User;
import com.springauth.domain.users.service.UserService;
import com.springauth.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	TokenService tokenService;	
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
		// Cria um objeto UsernamePasswordAuthenticationToken com email e senha. Este objeto será usado pelo AuthenticationManager para autenticar o usuário.
		var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
		
		// Spring Security valida o login:
		// 1. Busca o usuário via UserDetailsService (UserService.loadUserByUsername)
		// 2. Compara a senha com PasswordEncoder
		// 3. Se válido → retorna objeto Authentication com principal = User
		// 4. Se inválido → lança exception
		var authenticate = authenticationManager.authenticate(usernamePassword);
		
		// Gera um token JWT usando o usuário autenticado. O principal é convertido para User para obter email e role.
		var token = tokenService.generateToken((User) authenticate.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
		User newUser = new User(registerDTO.name(), registerDTO.email(), registerDTO.password(), registerDTO.role(), true);
		if(userService.createNewUser(newUser)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.ok().build().badRequest().build();
	}
}
