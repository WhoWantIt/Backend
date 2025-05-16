package gdg.whowantit.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    private String attachedImages;

    private String attachedExcelFile;

    private Boolean isVerified;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonatedItem> donatedItems = new ArrayList<>();

}
