package gdg.whowantit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FundingRelation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundingRelationId;

    private float paymentAmount;

    @ManyToOne
    @JoinColumn(name = "funding_id")
    private Funding funding;

    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
}
