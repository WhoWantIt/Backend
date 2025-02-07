package gdg.whowantit.repository;
import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    @Query("SELECT s FROM Sponsor s JOIN VolunteerRelation v ON s.sponsorId = v.sponsor.sponsorId " +
            "WHERE v.volunteer.volunteerId = :volunteerId")
    Page<Sponsor> findByVolunteerId(@Param("volunteerId") Long volunteerId, Pageable pageable);
}
