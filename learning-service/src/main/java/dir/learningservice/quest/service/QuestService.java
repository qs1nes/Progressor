package dir.learningservice.quest.service;

import dir.learningservice.common.exception.LearningPathNotFoundException;
import dir.learningservice.common.exception.QuestNotFoundException;
import dir.learningservice.common.exception.QuestStatusException;
import dir.learningservice.leaarning_path.entity.LearningPath;
import dir.learningservice.leaarning_path.entity.status.LearningStatus;
import dir.learningservice.leaarning_path.repository.LearningPathRepository;
import dir.learningservice.quest.dto.request.TemporaryRequest;
import dir.learningservice.quest.dto.response.QuestResponse;
import dir.learningservice.quest.entity.Quest;
import dir.learningservice.quest.entity.status.QuestStatus;
import dir.learningservice.quest.repository.QuestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestService {

    private final QuestRepository questRepository;
    private final LearningPathRepository learningPathRepository;

    @Transactional
    public QuestResponse createQuest(TemporaryRequest request) {
        LearningPath learningPath = findLearningPathById(request.learningPathId());

        validateLearningPathCanAcceptQuest(learningPath);

        Quest quest = Quest.builder()
                .title(request.title())
                .description(request.description())
                .experience(request.experience())
                .questStatus(QuestStatus.NOT_STARTED)
                .build();

        learningPath.addQuest(quest);

        Quest savedQuest = questRepository.save(quest);

        return map(savedQuest);
    }

    public QuestResponse getQuestById(Long questId) {
        Quest quest = findQuestById(questId);

        return map(quest);
    }

    public List<QuestResponse> getAllQuestsByLearningPathId(Long learningPathId) {
        findLearningPathById(learningPathId); // перевірка що topic існує, кине LearningPathNotFoundException якщо ні

        return questRepository.findAllByLearningPathId(learningPathId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestResponse startQuest(Long learningPathId ,Long questId) {
        Quest quest = findQuestById(questId);

        validateQuestBelongsToLearningPath(quest, learningPathId);

        if(quest.getQuestStatus() !=  QuestStatus.NOT_STARTED) {
            throw new QuestStatusException("Status already in progress or finished");
        }else{
            quest.setQuestStatus(QuestStatus.IN_PROGRESS);
        }

      return map(quest);
    }

    @Transactional
    public QuestResponse completeQuest(Long learningPathId ,Long questId) {
        Quest quest = findQuestById(questId);

        validateQuestBelongsToLearningPath(quest, learningPathId);

        if(quest.getQuestStatus() !=  QuestStatus.IN_PROGRESS) {
            throw new QuestStatusException("Status already finished or not started");
        }else{
            quest.setQuestStatus(QuestStatus.FINISHED);
        }

      return map(quest);
    }

    private Quest findQuestById(Long questId) {
        return questRepository.findById(questId)
                .orElseThrow(() ->
                        new QuestNotFoundException("Quest not found by  ID: " + questId));
    }

    private LearningPath findLearningPathById(Long learningPathId) {
        return learningPathRepository
                .findById(learningPathId)
                .orElseThrow(() ->
                        new LearningPathNotFoundException(
                                "LearningPath not found"
                        ));
    }

    private void validateQuestBelongsToLearningPath(Quest quest, Long learningPathId) {
        if(!quest.getLearningPath().getId() .equals(learningPathId)) {
            throw new QuestStatusException(
                    "Quest does not belong to LearningPath "
                            + learningPathId
            );
        }
    }

    private void validateLearningPathCanAcceptQuest(
            LearningPath learningPath
    ) {

        if (learningPath.getStatus() == LearningStatus.COMPLETED ||
                learningPath.getStatus() == LearningStatus.ARCHIVED) {

            throw new QuestStatusException(
                    "Cannot add quest to completed or archived LearningPath"
            );
        }
    }

    private QuestResponse map(Quest quest) {

        return new QuestResponse(
                quest.getId(),
                quest.getLearningPath().getId(),
                quest.getTitle(),
                quest.getDescription(),
                quest.getExperience(),
                quest.getQuestStatus()
        );
    }



}
