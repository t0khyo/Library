package com.t0khyo.library.service.impl;

import com.t0khyo.library.exception.EmailAlreadyExistException;
import com.t0khyo.library.exception.UserNameAlreadyExistException;
import com.t0khyo.library.mapper.UserMapper;
import com.t0khyo.library.model.dto.request.SignUpRequest;
import com.t0khyo.library.model.entity.Role;
import com.t0khyo.library.model.entity.User;
import com.t0khyo.library.repository.RoleRepository;
import com.t0khyo.library.repository.UserRepository;
import com.t0khyo.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public String register(SignUpRequest signUpRequest) throws RoleNotFoundException {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            log.warn("Username already used: {}", signUpRequest.username());
            throw new UserNameAlreadyExistException();
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            log.warn("User email already exists: {}", signUpRequest.email());
            throw new EmailAlreadyExistException();
        }

        Role userDefualtRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default ROLE_USER not found."));

        User user = userMapper.toEntity(signUpRequest);
        user.getRoles().add(userDefualtRole);

        userRepository.save(user);

        return "User: " + user.getUsername() + " created successfully.";
    }
}
