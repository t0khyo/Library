package com.t0khyo.library.controller;

import com.nimbusds.jose.JOSEException;
import com.t0khyo.library.model.dto.request.AuthRequest;
import com.t0khyo.library.model.dto.request.SignUpRequest;
import com.t0khyo.library.model.dto.response.AuthResponse;
import com.t0khyo.library.security.jwt.JwtUtil;
import com.t0khyo.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

            String token = jwtUtil.generateToken(authentication);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException | JOSEException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Validated @RequestBody SignUpRequest signUpRequest) throws RoleNotFoundException {
        return ResponseEntity.ok(userService.register(signUpRequest));
    }

}
