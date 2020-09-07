package ru.otus.spring.hw.application.business.rest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw.application.business.security.JwtTokenProvider;
import ru.otus.spring.hw.domain.business.dto.AuthResponse;
import ru.otus.spring.hw.domain.business.dto.LoginRequest;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final Counter loginCounter;
    private final Counter failLoginCounter;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                          MeterRegistry registry) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        loginCounter = registry.counter("counter.login.success");
        failLoginCounter = registry.counter("counter.login.failure");
    }

    @PostMapping("/api/auth")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()
                    )
            );
            loginCounter.increment();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(jwt, "Bearer"));
        } catch (Exception e) {
            failLoginCounter.increment();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
