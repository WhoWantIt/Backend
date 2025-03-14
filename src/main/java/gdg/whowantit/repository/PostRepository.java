package gdg.whowantit.repository;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBeneficiary_beneficiaryIdOrderByCreatedAtDesc(Long beneficiaryId);
    Page<Post> findByApprovalStatusOrderByCreatedAtDesc(ApprovalStatus approvalStatus, Pageable pageable);
    Page<Post> findByBeneficiaryOrderByCreatedAtDesc(Beneficiary beneficiary, Pageable pageable);
    Page<Post> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);
    @Query("SELECT p FROM Post p JOIN User u ON u.id = p.beneficiary.beneficiaryId " +
            "WHERE (:keyword1 IS NOT NULL AND u.nickname LIKE CONCAT('%', :keyword1, '%')) "
            )
    Page<Post> findByBeneficiaryNicknameOrderByCreatedAtDesc
            (@Param("keyword1") String keyword1, Pageable pageable);
  
    List<Post> findAllByApprovalStatusOrderByCreatedAtDesc(ApprovalStatus approvalStatus);


}


