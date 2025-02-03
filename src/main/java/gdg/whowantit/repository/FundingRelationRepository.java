package gdg.whowantit.repository;

import gdg.whowantit.entity.FundingRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingRelationRepository extends JpaRepository<FundingRelation, Long> {
    List<FundingRelation> findBySponsor_SponsorId(Long SponsorId);
}
