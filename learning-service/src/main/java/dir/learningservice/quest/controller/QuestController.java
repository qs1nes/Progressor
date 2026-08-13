package dir.learningservice.quest.controller;

import dir.learningservice.quest.dto.request.TemporaryRequest;
import dir.learningservice.quest.dto.response.QuestResponse;
import dir.learningservice.quest.entity.Quest;
import dir.learningservice.quest.service.QuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quest")
@Slf4j
@RequiredArgsConstructor
public class QuestController {
    private final QuestService questService;

    @PostMapping("/createQuest")
    public ResponseEntity<QuestResponse> createQuest(@Valid @RequestBody TemporaryRequest request) {
        QuestResponse quest = questService.createQuest(request);

        return ResponseEntity.ok(quest);
    }

    @GetMapping("/{questId}")
    public ResponseEntity<QuestResponse> getQuestById(@PathVariable("questId") Long questId) {
        QuestResponse response = questService.getQuestById(questId);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/learningPath/{learningPathId}")
    public ResponseEntity<List<QuestResponse>> getAllQuestsByLearningPathId(@PathVariable("learningPathId") Long learningPathId) {
        List<QuestResponse> response = questService.getAllQuestsByLearningPathId(learningPathId);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/startQuest/{learningPathId}/{questId}")
    public ResponseEntity<QuestResponse> startQuest(@PathVariable("learningPathId") Long learningPathId, @PathVariable("questId")  Long questId) {
        QuestResponse response = questService.startQuest(learningPathId, questId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/completeQuest/{learningPathId}/{questId}")
    public ResponseEntity<QuestResponse> completeQuest(@PathVariable("learningPathId") Long learningPathId, @PathVariable("questId")  Long questId) {
        QuestResponse response = questService.completeQuest(learningPathId, questId);

        return ResponseEntity.ok(response);
    }

}
