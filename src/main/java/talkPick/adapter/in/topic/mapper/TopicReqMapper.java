package talkPick.adapter.in.topic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import talkPick.adapter.out.topic.dto.TopicResDTO;
import talkPick.domain.topic.Topic;

@Mapper(componentModel = "spring")
public interface TopicReqMapper {

    talkPick.adapter.in.topic.mapper.TopicReqMapper INSTANCE = Mappers.getMapper(talkPick.adapter.in.topic.mapper.TopicReqMapper.class);

    TopicResDTO.Topic toTopicResponse(Topic topic);
}