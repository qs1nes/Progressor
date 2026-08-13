package dir.learningservice.leaarning_path.dto.response;

import dir.learningservice.leaarning_path.entity.status.Difficulty;
import dir.learningservice.leaarning_path.entity.status.LearningStatus;

import java.time.LocalDateTime;

public record LearningResponse(
        Long id,
        String name,
        Difficulty difficulty,
        Long experience,
        LocalDateTime startDate,
        LearningStatus status
) {}
