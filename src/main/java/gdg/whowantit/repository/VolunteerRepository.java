package gdg.whowantit.repository;

import gdg.whowantit.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    List<Volunteer> findByBeneficiary_beneficiaryId(Long beneficiaryId);
}
