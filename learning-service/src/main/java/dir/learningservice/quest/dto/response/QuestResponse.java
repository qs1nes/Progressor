package dir.learningservice.quest.dto.response;

import dir.learningservice.quest.entity.status.QuestStatus;

public record QuestResponse(
   Long id,
   Long learningPathId,
   String title,
   String description,
   Integer experience,
   QuestStatus status
) {}
