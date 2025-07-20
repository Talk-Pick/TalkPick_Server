package talkPick.domain.today.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.port.in.TodayTopicCommandUseCase;
import talkPick.domain.today.port.out.TodayTopicCommandRepositoryPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodayTopicCommandService implements TodayTopicCommandUseCase {
    private final TodayTopicCommandRepositoryPort todayTopicCommandRepositoryPort;

    @Override
    public List<TodayTopicResDTO.TopicSummaries> getTodayTopicSummaries(Long userId) {
        //TODO 오늘 저장한 topic개수가 10개인지 확인(새로고침 1번까지만 가능) 만약 10개 초과면 불가 x

        //TODO 오늘 조회한 TodayTopic들 다 가져와서 이를 제외하고 topic 5개 조회

        //TODO 조회한 5개 테이블에 저장
        return todayTopicCommandRepositoryPort.findTodayTopicSummaries(userId);
    }
}
