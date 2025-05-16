package gdg.whowantit.repository;

import gdg.whowantit.entity.DonatedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonatedItemRepository extends JpaRepository<DonatedItem, Long> {
    List<DonatedItem> findAllByPost_PostId(Long postId);
}
