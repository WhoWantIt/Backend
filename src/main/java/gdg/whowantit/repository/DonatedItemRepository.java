package gdg.whowantit.repository;

import gdg.whowantit.entity.DonatedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonatedItemRepository extends JpaRepository<DonatedItem, Long> {
}
