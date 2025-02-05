package gdg.whowantit.repository;

import gdg.whowantit.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findBySponsor_SponsorId(Long SponsorId);
    boolean existsBySponsor_SponsorIdAndVolunteer_VolunteerId(Long sponsorSponsorId, Long volunteerVolunteerId);
    void deleteBySponsor_SponsorIdAndVolunteer_VolunteerId(Long sponsorSponsorId, Long volunteerVolunteerId);
}
