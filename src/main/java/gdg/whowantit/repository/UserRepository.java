package gdg.whowantit.repository;

<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 5418def (feat: UserRepository 구현 (#16))
import gdg.whowantit.entity.RefreshToken;
import gdg.whowantit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);
    boolean existsByEmail(String email);
}
