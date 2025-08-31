package com.springauth.domain.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springauth.domain.users.entity.User;
import com.springauth.domain.users.repository.UserRepository;

// UserService implementa UserDetailsService, necessário para autenticação com Spring Security.
@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	// Método obrigatório de UserDetailsService. Chamado automaticamente pelo Spring Security durante o login.
    // Recebe o email do usuário e retorna um UserDetails (geralmente a entidade User implementa UserDetails).
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return repository.findByEmail(email);
	}
	
	public UserDetails getUserByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	// Método para criar um novo usuário com validação de email e senha criptografada.
	public boolean createNewUser(User newUser) {
		if(repository.findByEmail(newUser.getEmail()) != null) {
			return false;
		}
		
		// Cria um hash seguro da senha usando BCrypt, e substitui a senha original pela senha criptografada.
		String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword()); 
		newUser.setPassword(encryptedPassword);
		
		repository.save(newUser);
		
		return true;
	}
}
