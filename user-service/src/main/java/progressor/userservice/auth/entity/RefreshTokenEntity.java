package progressor.userservice.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_token")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;
    @Column(name = "expiration")
    private Instant expiration;
    private boolean revoke;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAuth user;
}
