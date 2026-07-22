package progressor.userservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for user registration")
public record RegisterRequest(
        @Schema(
                description = "Unique username(should be between 5 and 40)",
                example = "qsines"
        )
        @NotBlank
        @Size(min = 5, max = 40, message = "username should be in diapason between 5 and 40")
        String username,

        @Schema(
                description = "User email",
                example = "qsines@gmail.com"
        )
        @NotBlank
        @Email
        String email,

        @Schema(
                description = "User password(should be between 5 and 30)"
        )
        @NotBlank
        @Size(min = 5, max = 30)
        String password
) {}
