package com.t0khyo.library.mapper;

import com.t0khyo.library.model.dto.request.SignUpRequest;
import com.t0khyo.library.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(SignUpRequest signUpRequest);
}
