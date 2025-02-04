package gdg.whowantit.repository;

import gdg.whowantit.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
    Beneficiary findByBeneficiaryId(Long beneficiaryId);

}
