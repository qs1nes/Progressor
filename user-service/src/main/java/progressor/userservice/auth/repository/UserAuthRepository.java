package progressor.userservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progressor.userservice.auth.entity.UserAuth;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
    boolean existsByUsername(String username);
}
