package dir.learningservice.quest.dto.request;

import dir.learningservice.quest.entity.status.QuestStatus;

public record TemporaryRequest(
    String title,
    String description,
    Integer experience,
    QuestStatus questStatus,
    Long learningPathId
) {}
