package talkPick.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;

    // 서비스 약관 동의
    private boolean serviceTermAgree;
    private LocalDateTime serviceTermAgreeDate;

    // 개인정보 이용 약관 동의
    private boolean privateTermAgree;
    private LocalDateTime privateTermAgreeDate;

    // 나이 동의
    private boolean ageTermAgree;
    private LocalDateTime ageTermAgreeDate;

    // 마케팅 동의
    private boolean marketingTermAgree;
    private LocalDateTime marketingTermAgreeDate;

    // 이벤트혜택 푸시알림
    private boolean eventPush;
    private LocalDateTime eventPushDate;

    // 이벤트혜택 이메일알림
    private boolean eventEmailPush;
    private LocalDateTime eventEmailPushDate;

    // 이벤트혜택 문자알림
    private boolean eventSmsPush;
    private LocalDateTime eventSmsPushDate;
}