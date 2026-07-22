package progressor.userservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progressor.userservice.auth.entity.RefreshTokenEntity;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository <RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
