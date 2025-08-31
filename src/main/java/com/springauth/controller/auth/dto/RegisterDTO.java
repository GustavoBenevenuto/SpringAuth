package com.springauth.controller.auth.dto;

import com.springauth.domain.users.enums.UsersRoleEnum;

public record RegisterDTO(String name, String email, String password, UsersRoleEnum role) {

}
