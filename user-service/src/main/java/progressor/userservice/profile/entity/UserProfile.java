package progressor.userservice.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import progressor.userservice.auth.entity.UserAuth;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="user_profile")
public class UserProfile {
    @Id
    private Long id;
    @Column(name = "nickname",unique = true, nullable = false)
    private String nickname;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "statusMessage")
    private String statusMessage;
    @Column(name = "level")
    private Integer level;
    @Column(name = "bossesDefeated")
    private Integer bossesDefeated;
    @Column(name = "questsCompleted")
    private Integer questsCompleted;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserAuth user;

    @OneToMany(mappedBy = "userProfile",
               cascade = CascadeType.ALL)
    private List<LearningPath> learningPaths;
}
