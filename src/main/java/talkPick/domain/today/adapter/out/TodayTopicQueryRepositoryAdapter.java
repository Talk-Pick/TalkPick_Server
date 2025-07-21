package talkPick.domain.today.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.domain.today.adapter.in.dto.TodayTopicReqDTO;
import talkPick.domain.today.adapter.out.dto.TodayTopicResDTO;
import talkPick.domain.today.adapter.out.repository.TodayTopicQuerydslRepository;
import talkPick.domain.today.port.out.TodayTopicQueryRepositoryPort;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodayTopicQueryRepositoryAdapter implements TodayTopicQueryRepositoryPort {
    private final TodayTopicQuerydslRepository todayTopicQuerydslRepository;
//
//    @Override
//    public List<TodayTopicResDTO.TopicDetail> findTodayTopicDetails(TodayTopicReqDTO.TodayTopics requestDTO) {
//        return todayTopicQuerydslRepository.findTopicDetailsByIds(requestDTO);
//    }
}
