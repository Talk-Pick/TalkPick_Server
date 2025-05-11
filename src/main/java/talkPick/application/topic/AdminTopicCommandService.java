package talkPick.application.topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.adapter.in.topic.dto.TopicReqDTO;
import talkPick.adapter.in.topic.mapper.TopicReqMapper;
import talkPick.adapter.out.topic.TopicQueryRepositoryAdapter;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.domain.admin.Admin;
import talkPick.domain.topic.Topic;
import talkPick.port.in.topic.admin.AdminTopicCommandUseCase;
import talkPick.port.out.admin.AdminQueryRepositoryPort;
import talkPick.port.out.topic.TopicQueryRepositoryPort;
import talkPick.port.out.topic.admin.AdminTopicCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminTopicCommandService implements AdminTopicCommandUseCase {
    private final AdminTopicCommandRepositoryPort adminTopicCommandRepositoryPort;
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;
    private final AdminQueryRepositoryPort adminQueryRepositoryPort;
    private final TopicReqMapper topicReqMapper;

    @Override
    public TopicResDTO.Topic createTopicByAdmin(Long adminId, TopicReqDTO.CreateTopic createTopic) {
        Topic topic = adminTopicCommandRepositoryPort.save(
                Topic.create( // topic 생성
                        createTopic.title(),
                        createTopic.detail(),
                        createTopic.thumbnail(),
                        createTopic.icon(),
                        getValidAdmin(adminId)
                )
        );
        return topicReqMapper.toTopicResponse(topic);
    }

    @Override
    public TopicResDTO.Topic updateTopicByAdmin(Long topicId, TopicReqDTO.CreateTopic updateTopic) {

        // 해당 topic 찾아 업데이트
        Topic topic = getValidTopic(topicId);
        topic.update(updateTopic.title(), updateTopic.detail(), updateTopic.thumbnail(), updateTopic.icon());

        Topic updated = adminTopicCommandRepositoryPort.save(topic);
        return topicReqMapper.toTopicResponse(updated);
    }

    @Override
    public void deleteTopicByAdmin(Long topicId) {
        adminTopicCommandRepositoryPort.delete(topicId);
    }

    private Admin getValidAdmin(Long adminId) {
        return adminQueryRepositoryPort.findAdminById(adminId);
    }

    private Topic getValidTopic(Long topicId) {
        return topicQueryRepositoryPort.findTopicById(topicId);
    }
}
