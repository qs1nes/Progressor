package dir.learningservice.leaarning_path.dto.request;

import dir.learningservice.leaarning_path.entity.status.Difficulty;
import dir.learningservice.leaarning_path.entity.status.LearningStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record LearningRequest(
        @NotBlank
        @Size(min = 5, max = 40, message = "Task name should be in diapason between 5 and 40")
        String name,
        @NotNull(message = "status is necessary!")
        Difficulty difficulty
) {}
