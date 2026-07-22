package progressor.userservice.profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Column(name = "task_name")
    private String name;
    @Column(name = "task_level")
    private Integer level;
    @Column(name = "task_experience")
    private Long experience;
    @Column(name = "task_startedAt")
    private LocalDate startDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;


    @OneToMany(mappedBy = "LearningPath",
               cascade = CascadeType.ALL)
    private List<Quest> quests;
}
