package progressor.userservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response")
public record AuthResponse(
        @Schema(
                description = "JWT access token",
                example = "eyJhbGciOiJIUzI1NiJ9..."
        )
        String accessToken,

        @Schema(
                description = "JWT refresh token",
                example = "eyJhbGciOiJIUzI1NiJ9.."
        )
        String refreshToken
) {}
