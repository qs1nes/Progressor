package dir.learningservice.leaarning_path.controller;

import dir.learningservice.leaarning_path.dto.request.LearningRequest;
import dir.learningservice.leaarning_path.dto.response.LearningResponse;
import dir.learningservice.leaarning_path.entity.LearningPath;
import dir.learningservice.leaarning_path.service.LearningPathService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learning")
@Slf4j
@RequiredArgsConstructor
public class LearningPathController {

    private final LearningPathService learningPathService;

    @PostMapping("/createLearning")
    public ResponseEntity<LearningResponse> createLearningPath( @Valid @RequestBody LearningRequest learningRequest) {
        LearningResponse response  = learningPathService.createLearningPath(learningRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningResponse> findLearningPathById(@Valid @PathVariable Long id) {
        LearningResponse response = learningPathService.findLearningPathById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my/{userId}")
    public ResponseEntity<List<LearningResponse>> getMyLearningPaths(@PathVariable Long userId) {
        List<LearningResponse> responses = learningPathService.getMyLearningPaths(userId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LearningResponse>> getAll(){
        List<LearningResponse> responses = learningPathService.getAll();

        return ResponseEntity.ok(responses);
    }


    @PatchMapping("/start/{learningPathId}")
    public ResponseEntity<LearningResponse> startLearningPath(@PathVariable Long learningPathId) {
       LearningResponse response = learningPathService.startLearningPath(learningPathId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/complete/{learningPathId}")
    public ResponseEntity<LearningResponse> completeLearningPath(@PathVariable Long learningPathId) {
        LearningResponse response = learningPathService.completeLearningPath(learningPathId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/archive/{learningPathId}")
    public ResponseEntity<LearningResponse> archiveLearningPath(@PathVariable Long learningPathId) {
        LearningResponse response = learningPathService.archiveLearningPath(learningPathId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/allarchive/{userId}")
    public ResponseEntity<List<LearningResponse>> getAllArchivedLearningPath(@PathVariable Long userId) {
       List<LearningResponse> responses = learningPathService.getAllArchivedLearningPaths(userId);

       return ResponseEntity.ok(responses);
    }
}
