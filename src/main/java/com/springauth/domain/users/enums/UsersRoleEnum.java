package com.springauth.domain.users.enums;

//Define um enum chamado UsersRoleEnum para representar os papéis dos usuários na aplicação. 
public enum UsersRoleEnum {
	ADMIN("admin"),
	USER("user");
	
	private String role;
	
	UsersRoleEnum(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
