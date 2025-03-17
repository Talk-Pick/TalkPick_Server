package talkPick.domain.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopicLikeHistory is a Querydsl query type for TopicLikeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopicLikeHistory extends EntityPathBase<TopicLikeHistory> {

    private static final long serialVersionUID = -189716580L;

    public static final QTopicLikeHistory topicLikeHistory = new QTopicLikeHistory("topicLikeHistory");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final EnumPath<talkPick.model.TalkPickStatus> status = createEnum("status", talkPick.model.TalkPickStatus.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QTopicLikeHistory(String variable) {
        super(TopicLikeHistory.class, forVariable(variable));
    }

    public QTopicLikeHistory(Path<? extends TopicLikeHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopicLikeHistory(PathMetadata metadata) {
        super(TopicLikeHistory.class, metadata);
    }

}

