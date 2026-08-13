package dir.learningservice.leaarning_path.entity;

import dir.learningservice.leaarning_path.entity.status.Difficulty;
import dir.learningservice.leaarning_path.entity.status.LearningStatus;
import dir.learningservice.quest.entity.Quest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "learning_path")
public class LearningPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "task_name", unique = true, nullable = false)
    private String name;
    @Column(name = "task_total_experience")
    private Long totalExperience;
    @Column(name = "task_startedAt")
    private LocalDateTime startDate;
    @Column(name = "task_completedAt")
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    private LearningStatus status;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Builder.Default
    @OneToMany(mappedBy = "learningPath", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quest> quests = new ArrayList<>();

    public void addQuest(Quest quest) {
        quests.add(quest);
        quest.setLearningPath(this);
        totalExperience += quest.getExperience();
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
        quest.setLearningPath(null);
    }


}
