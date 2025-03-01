package talkPick.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminAuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String adminCode;

    private int loginAttemptCount;

    private boolean isLocked;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    // UUID 기반 adminCode 자동 생성
    private static String generateAdminCode() {
        return "talkpick" + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
    }

    public static AdminAuthInfo create(Admin admin, String password) {
        return AdminAuthInfo.builder()
                .admin(admin)
                .adminCode(generateAdminCode())
                .password(password)
                .loginAttemptCount(0)
                .isLocked(false)
                .build();
    }
}
