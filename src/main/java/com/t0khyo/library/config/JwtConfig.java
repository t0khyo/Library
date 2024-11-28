package com.t0khyo.library.config;

import com.t0khyo.library.security.jwt.JwtConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtConfigProperties.class)
public class JwtConfig {
}
