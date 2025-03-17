package talkPick.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberTopicHistory is a Querydsl query type for MemberTopicHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTopicHistory extends EntityPathBase<MemberTopicHistory> {

    private static final long serialVersionUID = 131844682L;

    public static final QMemberTopicHistory memberTopicHistory = new QMemberTopicHistory("memberTopicHistory");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    public final BooleanPath checkLiked = createBoolean("checkLiked");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final NumberPath<Long> talkTime = createNumber("talkTime", Long.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public final EnumPath<talkPick.domain.type.TopicType> topicType = createEnum("topicType", talkPick.domain.type.TopicType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QMemberTopicHistory(String variable) {
        super(MemberTopicHistory.class, forVariable(variable));
    }

    public QMemberTopicHistory(Path<? extends MemberTopicHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberTopicHistory(PathMetadata metadata) {
        super(MemberTopicHistory.class, metadata);
    }

}

