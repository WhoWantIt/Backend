package gdg.whowantit.repository;

import gdg.whowantit.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findVolunteerByVolunteerId(Long volunteerId);
}
