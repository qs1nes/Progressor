package progressor.userservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progressor.userservice.auth.dto.response.AuthResponse;
import progressor.userservice.auth.dto.request.LoginRequest;
import progressor.userservice.auth.dto.request.RegisterRequest;
import progressor.userservice.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns an access token together with a refresh token."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) throws Exception {
        log.debug("Registering request {}", request);
        log.info("Registering request {}", request);
        return ResponseEntity.ok(authService.signup(request));
    }

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates the user using username and password and returns an access token together with a refresh token."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws Exception {
        log.debug("Login request {}", request);
        return ResponseEntity.ok(authService.signin(request));
    }

    @Operation(
            summary = "User logout",
            description = "Change token status on invalid"
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) throws Exception {
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using a valid refresh token."
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String refreshToken) throws Exception {
        log.debug("Refreshing request {}", refreshToken);
        return ResponseEntity.ok(authService.newRefreshToken(refreshToken));
    }



}
