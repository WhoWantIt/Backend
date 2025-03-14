package gdg.whowantit.repository;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Funding;
import gdg.whowantit.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    List<Funding> findByBeneficiary_beneficiaryIdOrderByCreatedAtDesc(Long beneficiaryId);
    List<Funding> findAllByStatusOrderByCreatedAtDesc(Status status);
    List<Funding> findAllByApprovalStatusOrderByCreatedAtDesc(ApprovalStatus approvalStatus);
    List<Funding> findAllByOrderByCreatedAtDesc();
}
