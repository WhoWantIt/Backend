package gdg.whowantit.repository;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Field;
import gdg.whowantit.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findVolunteerByVolunteerIdOrderByCreatedAtDesc(Long volunteerId);
    Page<Volunteer> findByApprovalStatusOrderByCreatedAtDesc(ApprovalStatus approvalStatus, Pageable pageable);

    @Query("SELECT v FROM Volunteer v JOIN User u ON v.beneficiary.beneficiaryId = u.id  " +
            "WHERE (:keyword1 IS NULL OR u.address LIKE CONCAT('%', :keyword1, '%')) " +
            "AND (:keyword2 IS NULL OR u.address LIKE CONCAT('%', :keyword2, '%'))")
    Page<Volunteer> findByAddressContainingBothOrderByCreatedAtDesc
            (@Param("keyword1") String keyword1, @Param("keyword2") String keyword2, Pageable pageable);

    Page<Volunteer> findVolunteerByFieldOrderByCreatedAtDesc(Field field, Pageable pageable);
    List<Volunteer> findByBeneficiary_beneficiaryIdOrderByVolunteerIdDesc(Long beneficiaryId);

}
