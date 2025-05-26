package talkPick.domain.admin.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.domain.topic.adapter.out.dto.TopicResDTO;
import talkPick.domain.topic.domain.Topic;

@Mapper(componentModel = "spring")
public interface TopicReqMapper {

    TopicReqMapper INSTANCE = Mappers.getMapper(TopicReqMapper.class);

    TopicResDTO.Topic toTopicResponse(Topic topic);
}