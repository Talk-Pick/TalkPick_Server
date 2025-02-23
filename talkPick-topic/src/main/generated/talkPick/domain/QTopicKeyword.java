package talkPick.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopicKeyword is a Querydsl query type for TopicKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopicKeyword extends EntityPathBase<TopicKeyword> {

    private static final long serialVersionUID = 1082379843L;

    public static final QTopicKeyword topicKeyword = new QTopicKeyword("topicKeyword");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<talkPick.domain.type.Keyword> keyword = createEnum("keyword", talkPick.domain.type.Keyword.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QTopicKeyword(String variable) {
        super(TopicKeyword.class, forVariable(variable));
    }

    public QTopicKeyword(Path<? extends TopicKeyword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopicKeyword(PathMetadata metadata) {
        super(TopicKeyword.class, metadata);
    }

}

