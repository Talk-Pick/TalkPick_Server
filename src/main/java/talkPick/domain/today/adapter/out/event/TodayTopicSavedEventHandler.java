package talkPick.domain.today.adapter.out.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.today.adapter.out.repository.TodayTopicJpaRepository;
import talkPick.domain.today.domain.event.TodayTopicSavedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodayTopicSavedEventHandler {
    private final TodayTopicJpaRepository todayTopicJpaRepository;

    @Async
    @Transactional
    @EventListener
    public void handle(TodayTopicSavedEvent event) {
        try {
            if (event.getTodayTopics() != null && !event.getTodayTopics().isEmpty()) {
                todayTopicJpaRepository.saveAll(event.getTodayTopics());
            }
        } catch (Exception e) {
            log.error("오늘의 주제 저장 실패", e);
        }
    }
}