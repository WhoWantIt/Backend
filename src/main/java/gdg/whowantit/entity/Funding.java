package gdg.whowantit.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Funding extends BaseEntity {
    @Id
    private Long fundingId;

    private String content;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String productName;

    private Float targetAmount;

    private Float currentAmount;

    private String attachedImage;

    private ApprovalStatus approvalStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL)
    private List<FundingRelation> fundingRelations;
}
