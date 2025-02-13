package talkPick.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSetting {
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