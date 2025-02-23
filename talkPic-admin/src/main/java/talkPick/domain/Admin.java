package talkPick.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.domain.type.Role;
import talkPick.model.BaseTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String adminCode;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("loginTime DESC") // 최근 로그인 순서
    private List<AdminLoginHistory> loginHistories = new ArrayList<>();

    // adminCode 자동 생성
    @PrePersist
    public void generateAdminCode() {
        if (this.adminCode == null) {
            this.adminCode = "talkpick" + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        }
    }
}
