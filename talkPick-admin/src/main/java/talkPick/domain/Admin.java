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
    private long id;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private AdminAuthInfo authInfo;

    @Builder.Default
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("loginTime DESC") // 최근 로그인 순서
    private List<AdminLoginHistory> loginHistories = new ArrayList<>();

    public void updateAuthInfo(AdminAuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public static Admin create(String email) {
        return Admin.builder()
                .email(email)
                .role(Role.ADMIN)
                .build();
    }

    public static Admin toSignupAdminEntity(String email) {
        return Admin.builder()
                .email(email)
                .role(Role.ADMIN)
                .build();
    }
}
