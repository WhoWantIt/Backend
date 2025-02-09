package gdg.whowantit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Beneficiary {
    @Id
    private Long beneficiaryId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "beneficiary_id")
    private User user;

    private String info;

    private Long toddler;

    private Long child;

    private Long adolescent;

    private Long youth;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>(); //beneficiary와 Post 간의 1:N 관계

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funding> fundings = new ArrayList<>(); //beneficiary와 Funding 간의 1:N 관계


    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Volunteer> volunteers = new ArrayList<>(); //beneficiary와 Volunteer 간의 1:N 관계

}
