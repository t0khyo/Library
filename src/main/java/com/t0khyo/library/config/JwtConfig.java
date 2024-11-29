package com.t0khyo.library.config;

import com.t0khyo.library.security.jwt.JwtConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtConfigProperties.class)
public class JwtConfig {
}
