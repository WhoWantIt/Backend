package gdg.whowantit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true)
    private String email; // RefreshToken 발급 대상

    @Column(nullable = false, unique = true)
    private String token; // RefreshToken 값

    @Column(nullable = false)
    private Long expiration; // 만료 시간 (Unix 타임스탬프)
}
