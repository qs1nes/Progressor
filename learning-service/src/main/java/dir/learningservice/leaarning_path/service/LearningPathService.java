package dir.learningservice.leaarning_path.service;

import dir.learningservice.common.exception.LearningPathNotFoundException;
import dir.learningservice.common.exception.TaskStatusException;
import dir.learningservice.leaarning_path.dto.request.LearningRequest;
import dir.learningservice.leaarning_path.dto.response.LearningResponse;
import dir.learningservice.leaarning_path.entity.LearningPath;
import dir.learningservice.leaarning_path.entity.status.LearningStatus;
import dir.learningservice.leaarning_path.repository.LearningPathRepository;
import dir.learningservice.quest.repository.QuestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class LearningPathService {

    private final LearningPathRepository learningPathRepository;
    private final QuestRepository questRepository;

    @Transactional
    public LearningResponse createLearningPath(LearningRequest learningRequest) {
        LearningPath learningTopic = LearningPath.builder()
                .name(learningRequest.name())
                .totalExperience(0L)
                .difficulty(learningRequest.difficulty())
                .status(LearningStatus.NOT_STARTED)
                .build();

        LearningPath saved = learningPathRepository.save(learningTopic);

        return map(saved);
    }

    public LearningResponse findLearningPathById(Long id) {
        LearningPath learningPath = findEntityById(id);

        return map(learningPath);
    }

    public List<LearningResponse> getMyLearningPaths(Long userId) {
        return learningPathRepository.findAllByUserId(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<LearningResponse> getAll(){
        return learningPathRepository.findAll().stream().map(this::map).toList();
    }

    @Transactional
    public LearningResponse startLearningPath(Long learningPathId){
        LearningPath learningPath = findEntityById(learningPathId);

        if(learningPath.getStatus() == LearningStatus.NOT_STARTED){
            learningPath.setStatus(LearningStatus.STARTED);
            learningPath.setStartDate(LocalDateTime.now());
        }else
            throw new TaskStatusException("LearningPath with id " + learningPathId + " already started");


        return map(learningPath);
    }

    @Transactional
    public LearningResponse completeLearningPath(Long learningPathId){
        LearningPath learningPath = findEntityById(learningPathId);

        if(learningPath.getStatus() == LearningStatus.STARTED ||  learningPath.getStatus() == LearningStatus.IN_PROGRESS){
            learningPath.setStatus(LearningStatus.COMPLETED);
            learningPath.setCompletedAt(LocalDateTime.now());
        }else
            throw new  TaskStatusException("LearningPath with id " + learningPathId + " already completed");

        return map(learningPath);
    }

    @Transactional
    public LearningResponse archiveLearningPath(Long learningPathId) {

        LearningPath learningPath = findEntityById(learningPathId);

        if (learningPath.getStatus() != LearningStatus.COMPLETED) {
            throw new TaskStatusException(
                    "LearningPath must be completed before archiving"
            );
        }

        learningPath.setStatus(LearningStatus.ARCHIVED);

        return map(learningPath);
    }

    public List<LearningResponse> getAllArchivedLearningPaths(Long userId) {
        return learningPathRepository.findAllByStatusAndUserId(LearningStatus.ARCHIVED,userId)
                .stream()
                .map(this::map)
                .toList();
    }





    private LearningResponse map(LearningPath learningPath) {
        return new LearningResponse(
                learningPath.getId(),
                learningPath.getName(),
                learningPath.getDifficulty(),
                learningPath.getTotalExperience(),
                learningPath.getStartDate(),
                learningPath.getStatus()
        );
    }

    private LearningPath findEntityById(Long id) {
        return learningPathRepository.findById(id)
                .orElseThrow(() ->
                        new LearningPathNotFoundException(
                                "LearningPath with id " + id + " not found"
                        ));
    }
}
