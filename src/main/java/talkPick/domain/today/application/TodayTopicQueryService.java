package talkPick.domain.today.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import talkPick.domain.today.adapter.in.dto.TodayTopicReqDTO;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.port.in.TodayTopicQueryUseCase;
import talkPick.domain.today.port.out.TodayTopicQueryRepositoryPort;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodayTopicQueryService implements TodayTopicQueryUseCase {
    private final TodayTopicQueryRepositoryPort todayTopicQueryRepositoryPort;

//    @Override
//    public List<TodayTopicResDTO.TopicDetail> getTodayTopicDetails(TodayTopicReqDTO.TodayTopics requestDTO) {
//        return todayTopicQueryRepositoryPort.findTodayTopicDetails(requestDTO);
//    }
}
