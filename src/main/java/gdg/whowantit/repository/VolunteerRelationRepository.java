package gdg.whowantit.repository;

import gdg.whowantit.entity.VolunteerRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerRelationRepository extends JpaRepository<VolunteerRelation, Long> {
    List<VolunteerRelation> findBySponsor_SponsorId(Long SponsorId);
}
