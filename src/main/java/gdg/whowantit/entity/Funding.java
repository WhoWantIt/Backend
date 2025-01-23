package gdg.whowantit.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

public class Funding {
    @Id
    private int fundingId;

    private String content;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String productName;

    private Float targetAmount;

    private String attachedImage;

    private boolean approval;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL)
    private List<FundingRelation> fundingRelations;
}
