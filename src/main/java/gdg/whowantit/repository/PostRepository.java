package gdg.whowantit.repository;

import gdg.whowantit.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBeneficiary_beneficiaryId(Long beneficiaryId);
}
