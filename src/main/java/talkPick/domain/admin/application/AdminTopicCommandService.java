package talkPick.domain.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.admin.adapter.in.mapper.TopicReqMapper;
import talkPick.domain.admin.domain.Admin;
import talkPick.domain.admin.port.in.AdminTopicCommandUseCase;
import talkPick.domain.admin.port.out.AdminQueryRepositoryPort;
import talkPick.domain.admin.port.out.AdminTopicCommandRepositoryPort;
import talkPick.domain.topic.adapter.in.dto.TopicReqDTO;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.Topic;
import talkPick.domain.topic.port.out.TopicQueryRepositoryPort;
import talkPick.domain.topic.port.out.TopicStatCommandRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminTopicCommandService implements AdminTopicCommandUseCase {
    private final AdminTopicCommandRepositoryPort adminTopicCommandRepositoryPort;
    private final TopicStatCommandRepositoryPort topicStatCommandRepositoryPort;
    private final TopicQueryRepositoryPort topicQueryRepositoryPort;
    private final AdminQueryRepositoryPort adminQueryRepositoryPort;
    private final TopicReqMapper topicReqMapper;

    @Override
    public TopicResDTO.Topic createTopicByAdmin(Long adminId, TopicReqDTO.Create create) {
        Topic topic = adminTopicCommandRepositoryPort.save(
                Topic.create( // topic 생성
                        create.title(),
                        create.detail(),
                        create.thumbnail(),
                        create.icon(),
                        adminId
                )
        );
        topicStatCommandRepositoryPort.save(topic.getId()); // topic 통계 생성
        return topicReqMapper.toTopicResponse(topic);
    }

    @Override
    public TopicResDTO.Topic updateTopicByAdmin(Long topicId, TopicReqDTO.Create updateTopic) {

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