package talkPick.domain.admin.domain;

import jakarta.persistence.*;
import lombok.*;
import talkPick.global.model.BaseTime;
import talkPick.domain.admin.domain.type.Role;

import java.util.ArrayList;
import java.util.List;

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
    private String name;

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

    public static Admin create(String email, String name) {
        return Admin.builder()
                .email(email)
                .name(name)
                .role(Role.ADMIN)
                .build();
    }
}
