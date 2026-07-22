package progressor.userservice.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progressor.userservice.auth.dto.response.AuthResponse;
import progressor.userservice.auth.dto.request.LoginRequest;
import progressor.userservice.auth.dto.request.RegisterRequest;
import progressor.userservice.auth.entity.role.Role;
import progressor.userservice.auth.entity.RefreshTokenEntity;
import progressor.userservice.auth.entity.UserAuth;
import progressor.userservice.auth.repository.TokenRepository;
import progressor.userservice.auth.repository.UserAuthRepository;
import progressor.userservice.profile.service.UserProfileService;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserAuthRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserProfileService userProfileService;

    @Transactional
    public AuthResponse signup(RegisterRequest request) throws Exception {
        log.info("Attempting to register a new user with username: '{}' and email: '{}'", request.username(), request.email());
        if(userRepository.existsByUsername(request.username())){
            log.warn("Registration failed: Username '{}' is already taken", request.username());
            throw new Exception("Username is already in use");
        }

        UserAuth userAuth = UserAuth.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userProfileService.createDefaultProfile(userAuth);

        UserAuth savedUser = userRepository.save(userAuth);
        log.info("User '{}' successfully registered with ID: {}", savedUser.getUsername(), savedUser.getId());


        String accessT = jwtService.generateAccessToken(savedUser);
        String refreshT = jwtService.generateRefreshToken(savedUser);

        saveRefreshToken(savedUser, refreshT);
        log.info("Token successfully saved(after signup) for user '{}'", savedUser.getUsername());

        return new AuthResponse(
                accessT,
                refreshT
        );
    }

    public AuthResponse signin(LoginRequest request) throws Exception {
        log.info("Attemting to authenticate with username: '{}'", request.username());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserAuth user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> {
                    log.error("Authentication internal error: User '{}' validated by AuthManager but not found in DB", request.username());
                    return new Exception("Username not found");
                });
        log.info("User '{}' successfully authenticated", request.username());

        String accessT = jwtService.generateAccessToken(user);
        String refreshT = jwtService.generateRefreshToken(user);

        saveRefreshToken(user, refreshT);
        log.info("Token successfully saved(after signin) for user '{}'", user.getUsername());

        return  new AuthResponse(
               accessT,
                refreshT
        );
    }

    @Transactional
    public void logout(String RefreshToken) throws Exception {
        RefreshTokenEntity token = tokenRepository
                .findByRefreshToken(RefreshToken)
                .orElseThrow(() -> new Exception("Token already revoked"));

        token.setRevoke(true);
    }

    @Transactional
    public AuthResponse newRefreshToken(String refreshToken) throws Exception {
        log.debug("Received token refresh request");

        RefreshTokenEntity token = tokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() -> new Exception("Refresh token not found"));

        UserAuth user = token.getUser();

        if(token.isRevoke()){
            log.info("Token already revoked");
            throw new Exception("Token is revoked");
        }

        if (token.getExpiration().isBefore(Instant.now())) {
            throw new Exception("Refresh token expired");
        }

        if(!jwtService.isTokenValid(refreshToken, user)){
            log.warn("Refresh internal error: Invalid token");
            throw new Exception("Invalid refresh token");
        }

        token.setRevoke(true);
        tokenRepository.save(token);

        String accessT = jwtService.generateAccessToken(user);
        String refreshT = jwtService.generateRefreshToken(user);

        saveRefreshToken(user, refreshT);
        log.info("Tokens successfully refreshed for user: '{}'", user.getUsername());

        return  new AuthResponse(
                accessT,
                refreshT
        );

    }


    private RefreshTokenEntity saveRefreshToken(UserAuth user, String refreshToken) {
        log.debug("Attempting to save refresh token for user '{}'", user.getUsername());
        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .refreshToken(refreshToken)
                .expiration(jwtService.getRefreshTokenExpiration(refreshToken))
                .revoke(false)
                .user(user)
                .build();

        return tokenRepository.save(entity);
    }

}
