package talkPick.domain.admin;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime loginTime;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private boolean successful;

    @Column
    private String failureReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @PrePersist
    public void setLoginTime() {
        if (this.successful) { // 로그인 성공시에만 기록 남김
            this.loginTime = LocalDateTime.now();
        }
    }
}
