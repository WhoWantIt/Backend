package gdg.whowantit.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Funding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundingId;

    private String content;

    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status= Status.BEFORE_PROGRESS;

    private String productName;

    private Float targetAmount;

    private Float currentAmount;

    private String attachedImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus= ApprovalStatus.UNDETERMINED;

    private LocalDateTime deadline;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL)
    private List<FundingRelation> fundingRelations;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL)
    private List<FundingScrap> fundingScraps;
}
