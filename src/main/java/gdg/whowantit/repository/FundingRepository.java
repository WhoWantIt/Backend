package gdg.whowantit.repository;

import gdg.whowantit.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    List<Funding> findByBeneficiary_beneficiaryId (Long beneficiaryId);
}
