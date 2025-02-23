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

    public final NumberPath<Integer> ECount = createNumber("ECount", Integer.class);

    public final NumberPath<Integer> FCount = createNumber("FCount", Integer.class);

    public final NumberPath<Integer> ICount = createNumber("ICount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> JCount = createNumber("JCount", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> NCount = createNumber("NCount", Integer.class);

    public final NumberPath<Integer> PCount = createNumber("PCount", Integer.class);

    public final NumberPath<Integer> SCount = createNumber("SCount", Integer.class);

    public final NumberPath<Integer> selectCount = createNumber("selectCount", Integer.class);

    public final EnumPath<talkPick.model.TalkPickStatus> status = createEnum("status", talkPick.model.TalkPickStatus.class);

    public final NumberPath<Integer> TCount = createNumber("TCount", Integer.class);

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

