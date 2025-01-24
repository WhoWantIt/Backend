package gdg.whowantit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Sponsor {
    @Id
    private Long sponsorId;

    @OneToOne
    @MapsId //  id 를 User의 id로 매핑
    @JoinColumn(name = "sponsor_id") // 외래 키
    private User user;

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private List<Scrap> scraps;

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private List<VolunteerRelation> volunteerRelations;

    @OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private List<FundingRelation> fundingRelations;

    private String image;
}
