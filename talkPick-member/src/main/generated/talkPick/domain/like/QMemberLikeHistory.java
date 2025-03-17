package talkPick.domain.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberLikeHistory is a Querydsl query type for MemberLikeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberLikeHistory extends EntityPathBase<MemberLikeHistory> {

    private static final long serialVersionUID = -122323851L;

    public static final QMemberLikeHistory memberLikeHistory = new QMemberLikeHistory("memberLikeHistory");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final EnumPath<talkPick.model.TalkPickStatus> status = createEnum("status", talkPick.model.TalkPickStatus.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QMemberLikeHistory(String variable) {
        super(MemberLikeHistory.class, forVariable(variable));
    }

    public QMemberLikeHistory(Path<? extends MemberLikeHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberLikeHistory(PathMetadata metadata) {
        super(MemberLikeHistory.class, metadata);
    }

}

