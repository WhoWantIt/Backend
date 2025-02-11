package gdg.whowantit.repository;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Post;
import gdg.whowantit.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBeneficiary_beneficiaryId(Long beneficiaryId);
    Page<Post> findByApprovalStatus(ApprovalStatus approvalStatus, Pageable pageable);
    Page<Post> findByBeneficiary(Beneficiary beneficiary, Pageable pageable);
    Page<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    @Query("SELECT p FROM Post p JOIN User u ON u.id = p.beneficiary.beneficiaryId " +
            "WHERE (:keyword1 IS NOT NULL AND u.nickname LIKE CONCAT('%', :keyword1, '%')) "
            )
    Page<Post> findByBeneficiaryNickname
            (@Param("keyword1") String keyword1, Pageable pageable);

}


