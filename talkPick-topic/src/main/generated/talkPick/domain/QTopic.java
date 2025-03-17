package talkPick.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopic is a Querydsl query type for Topic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopic extends EntityPathBase<Topic> {

    private static final long serialVersionUID = -1898242874L;

    public static final QTopic topic = new QTopic("topic");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    public final NumberPath<Long> averageTalkTime = createNumber("averageTalkTime", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> ECount = createNumber("ECount", Long.class);

    public final NumberPath<Long> FCount = createNumber("FCount", Long.class);

    public final NumberPath<Long> ICount = createNumber("ICount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> JCount = createNumber("JCount", Long.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final NumberPath<Long> NCount = createNumber("NCount", Long.class);

    public final NumberPath<Long> PCount = createNumber("PCount", Long.class);

    public final NumberPath<Long> SCount = createNumber("SCount", Long.class);

    public final NumberPath<Long> selectCount = createNumber("selectCount", Long.class);

    public final EnumPath<talkPick.model.TalkPickStatus> status = createEnum("status", talkPick.model.TalkPickStatus.class);

    public final NumberPath<Long> TCount = createNumber("TCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QTopic(String variable) {
        super(Topic.class, forVariable(variable));
    }

    public QTopic(Path<? extends Topic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopic(PathMetadata metadata) {
        super(Topic.class, metadata);
    }

}

