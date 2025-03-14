package gdg.whowantit.repository;

import gdg.whowantit.entity.FundingRelation;
import gdg.whowantit.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FundingRelationRepository extends JpaRepository<FundingRelation, Long> {
    List<FundingRelation> findBySponsor_SponsorIdOrderByFundingRelationIdDesc(Long sponsorId);
    List<FundingRelation> findAllByFunding_FundingIdOrderByFundingRelationIdDesc(Long fundingId);
    Optional<FundingRelation> findTopBySponsorOrderByFundingRelationIdDesc(Sponsor sponsor);
}
