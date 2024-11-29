package com.t0khyo.library.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Decide whether the filter should be applied.
        final String authHeader = request.getHeader("Authentication");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.error("No JWT accessToken found in request headers or accessToken format is invalid.");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Apply filter: authenticate or reject request
        final String jwt = authHeader.substring(7);
        final String username;
        final SignedJWT signedJWT;

        try {
            signedJWT = jwtUtil.validateToken(jwt);
            username = jwtUtil.extractUsername(signedJWT);

            if (username != null && !username.isBlank()
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (ParseException e) {
            logger.error("JWT token is malformed: {}", e);
            setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, "Malformed JWT token.");
            return;
        } catch (JOSEException e) {
            logger.error("Error validating JWT signature: {}", e);
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, "Invalid JWT signature.");
            return;
        } catch (RuntimeException e) {
            logger.error("Unexpected error during JWT validation: {}", e);
            setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response, "Unexpected error occurred.");
            return;
        }

        // 3. Invoke the rest of the chain
        filterChain.doFilter(request, response);

    }

    private void setErrorResponse(int status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
