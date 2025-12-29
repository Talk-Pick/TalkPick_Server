package talkPick.batch.member.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.in.MemberWithdrawalUseCase;
import talkPick.global.model.TalkPickStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class MemberCleanupScheduler {

    private final MemberJpaRepository memberRepository;
    private final MemberWithdrawalUseCase memberWithdrawalUseCase;

    /**
     * 매일 새벽 3시에 탈퇴한 지 15일이 지난 회원을 영구 삭제합니다.
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupExpiredMembers() {
        log.info("회원 탈퇴 데이터 정리 스케줄러 시작");
        
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(15);
        Optional<Member> expiredMember = memberRepository.findTop1ByStatusAndDeletedAtBefore(TalkPickStatus.DIS_ACTIVE, thresholdDate);

        if (expiredMember.isPresent()) {
            Member member = expiredMember.get();
            try {
                log.info("삭제 대상 회원 ID: {}", member.getId());
                memberWithdrawalUseCase.hardDelete(member.getId());
                log.info("회원 ID {} 영구 삭제 완료", member.getId());
            } catch (Exception e) {
                log.error("회원 ID {} 영구 삭제 중 오류 발생", member.getId(), e);
            }
        } else {
            log.info("삭제 대상 회원이 없습니다.");
        }

        log.info("회원 탈퇴 데이터 정리 스케줄러 종료");
    }
}
