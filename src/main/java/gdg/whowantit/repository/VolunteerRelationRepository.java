package gdg.whowantit.repository;

import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.Volunteer;
import gdg.whowantit.entity.VolunteerRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRelationRepository extends JpaRepository<VolunteerRelation, Long> {
    Optional<VolunteerRelation> findVolunteerRelationByVolunteer(Volunteer volunteer);
    boolean existsVolunteerRelationBySponsor(Sponsor sponsor);
    void deleteVolunteerRelationByVolunteerRelationId(Long volunteerRelationId);
    Optional<VolunteerRelation> findByVolunteerAndSponsor(Volunteer volunteer, Sponsor sponsor);
}
