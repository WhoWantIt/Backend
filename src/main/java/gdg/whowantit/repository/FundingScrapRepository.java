package gdg.whowantit.repository;

import gdg.whowantit.entity.FundingScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingScrapRepository extends JpaRepository<FundingScrap, Long> {
    List<FundingScrap> findBySponsor_SponsorId(Long SponsorId);
}
