package gdg.whowantit.repository;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBeneficiary_beneficiaryId(Long beneficiaryId);
    Page<Post> findByApprovalStatus(ApprovalStatus approvalStatus, Pageable pageable);
    Page<Post> findByBeneficiary(Beneficiary beneficiary, Pageable pageable);
}
