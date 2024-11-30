package com.t0khyo.library.service;

import com.t0khyo.library.model.dto.request.SignUpRequest;

import javax.management.relation.RoleNotFoundException;

public interface UserService {
    String register(SignUpRequest signUpRequest) throws RoleNotFoundException;

}
