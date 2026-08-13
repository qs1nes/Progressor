package dir.learningservice.quest.entity;

import dir.learningservice.leaarning_path.entity.LearningPath;
import dir.learningservice.quest.entity.status.QuestStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="user_quest")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quest_title", unique = true, nullable = false)
    private String title;
    @Column(name = "quest_description", nullable = false)
    private String description;
    @Column(name = "quest_experience")
    private Integer experience;

    @Enumerated(EnumType.STRING)
    private QuestStatus questStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id")
    private LearningPath learningPath;
}


