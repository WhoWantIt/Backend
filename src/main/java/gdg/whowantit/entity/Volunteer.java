package gdg.whowantit.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Volunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long volunteerId;

    @Enumerated(EnumType.STRING)
    private Field field;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String content;

    private String title;

    private LocalDateTime deadline;

    private String attachedImage;

    private boolean approval;

    private Long maxCapacity;

    private Long currentCapacity;

    private LocalDateTime startTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<VolunteerRelation> volunteerRelations;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Scrap> scraps;
}
