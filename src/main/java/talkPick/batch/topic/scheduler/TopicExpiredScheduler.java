package talkPick.batch.topic.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import talkPick.core.common.model.TalkPickStatus;
import talkPick.domain.topic.adapter.out.repository.TopicJpaRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopicExpiredScheduler {

    private final TopicJpaRepository topicJpaRepository;

    /**
     * 매 시간 정각에 만료 기간이 지난 ACTIVE 상태의 Topic을 DIS_ACTIVE로 처리합니다.
     */
    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void deactivateExpiredTopics() {
        log.info("만료 Topic 비활성화 스케줄러 시작");

        int count = topicJpaRepository.expireTopics(
                TalkPickStatus.ACTIVE,
                TalkPickStatus.DIS_ACTIVE,
                LocalDateTime.now()
        );

        log.info("만료 Topic 비활성화 완료 - 처리 건수: {}", count);
    }
}