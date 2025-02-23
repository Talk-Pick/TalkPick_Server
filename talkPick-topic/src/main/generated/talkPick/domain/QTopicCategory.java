package talkPick.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopicCategory is a Querydsl query type for TopicCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopicCategory extends EntityPathBase<TopicCategory> {

    private static final long serialVersionUID = -1278601244L;

    public static final QTopicCategory topicCategory = new QTopicCategory("topicCategory");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    public final EnumPath<talkPick.domain.type.Category> category = createEnum("category", talkPick.domain.type.Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QTopicCategory(String variable) {
        super(TopicCategory.class, forVariable(variable));
    }

    public QTopicCategory(Path<? extends TopicCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopicCategory(PathMetadata metadata) {
        super(TopicCategory.class, metadata);
    }

}

