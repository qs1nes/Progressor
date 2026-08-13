package dir.learningservice.leaarning_path.repository;

import dir.learningservice.leaarning_path.entity.LearningPath;
import dir.learningservice.leaarning_path.entity.status.LearningStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LearningPathRepository extends JpaRepository<LearningPath,Long> {
    List<LearningPath> findAllByUserId(Long userId);
    List<LearningPath> findAll();
    List<LearningPath> findAllByStatusAndUserId(LearningStatus status, Long userId);
}

