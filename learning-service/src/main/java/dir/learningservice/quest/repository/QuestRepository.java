package dir.learningservice.quest.repository;

import dir.learningservice.quest.dto.response.QuestResponse;
import dir.learningservice.quest.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
   List<Quest> findAllByLearningPathId(Long learningPathId);
}
