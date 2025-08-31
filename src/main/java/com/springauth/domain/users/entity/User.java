package com.springauth.domain.users.entity;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springauth.domain.users.enums.UsersRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Implementa UserDetails, permitindo que o Spring Security use essa entidade para autenticação.
 * Gera construtores sem argumentos e com todos os argumento
 * */
@Table(name = "tb_users")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
	
	private static final long serialVersionUID = 8556477070207266590L;

	public User(String name, String email, String password, UsersRoleEnum role, Boolean active) {
	    this.name = name;
	    this.email = email;
	    this.password = password;
	    this.role = role;
	    this.active = active != null ? active : true; // padrão true se não fornecido
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private UsersRoleEnum role; // ex: ROLE_USER, ROLE_ADMIN
    
    @Column(nullable = false)
    private Boolean active = true;

    @Column
    private LocalDateTime lastLogin;

	@CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

	@UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

	/**
	 * Método obrigatório da interface UserDetails. Retorna as "authorities" (permissões/roles) do usuário, usadas pelo Spring Security
	 * para decidir se ele pode acessar certos endpoints.
	 * */ 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == UsersRoleEnum.ADMIN) { // Todo ADMIN também recebe permissão de USER
			return List.of(
						new SimpleGrantedAuthority("ROLE_USER"),
						new SimpleGrantedAuthority("ROLE_ADMIN")
					);
		}
		return List.of(new SimpleGrantedAuthority("ROLE_USER")); // Se não for ADMIN, usuário normal recebe apenas ROLE_USER.
	}

	@Override
	public String getUsername() {
		return email;
	}
	
}

