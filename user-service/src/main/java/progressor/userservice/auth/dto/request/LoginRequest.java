package progressor.userservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request for user authentication")
public record LoginRequest(
        @Schema(
                description = "Username of the registered user",
                example = "qsines"
        )
        @NotBlank
        String username,

        @Schema(
                description = "User password of the registered user_password"
        )
        @NotBlank
        String password
) {}
