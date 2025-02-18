package gdg.whowantit.repository;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Funding;
import gdg.whowantit.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    List<Funding> findByBeneficiary_beneficiaryId (Long beneficiaryId);
    List<Funding> findAllByStatus(Status status);
    List<Funding> findAllByApprovalStatus(ApprovalStatus approvalStatus);
}
