package progressor.userservice.profile.entity;

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
    @Column(name = "quest_title")
    private String title;
    @Column(name = "quest_difficulty")
    private String difficulty;
    @Column(name = "quest_description")
    private String description;
    @Column(name = "quest_damage")
    private Integer damage;
    @Column(name = "quest_experience")
    private Integer experience;
    @Column(name = "quest_status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private LearningPath learningPath;
}
