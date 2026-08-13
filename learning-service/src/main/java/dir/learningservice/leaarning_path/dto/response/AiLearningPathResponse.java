package dir.learningservice.leaarning_path.dto.response;

import dir.learningservice.leaarning_path.entity.status.Difficulty;
import dir.learningservice.quest.dto.response.AIQuestDto;

import java.util.List;

public record AiLearningPathResponse(
        String name,
        Difficulty difficulty,
        Long totalExperience,
        List<AIQuestDto> quests
) {}
