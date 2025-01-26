package gdg.whowantit.repository;

import gdg.whowantit.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String userEmail);
    Optional<RefreshToken> findByToken(String token);
    void deleteByEmail(String userEmail);
}