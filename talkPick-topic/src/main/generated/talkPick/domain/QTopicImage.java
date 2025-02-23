package talkPick.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopicImage is a Querydsl query type for TopicImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopicImage extends EntityPathBase<TopicImage> {

    private static final long serialVersionUID = -1613912011L;

    public static final QTopicImage topicImage = new QTopicImage("topicImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final EnumPath<talkPick.model.TalkPickStatus> status = createEnum("status", talkPick.model.TalkPickStatus.class);

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public QTopicImage(String variable) {
        super(TopicImage.class, forVariable(variable));
    }

    public QTopicImage(Path<? extends TopicImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopicImage(PathMetadata metadata) {
        super(TopicImage.class, metadata);
    }

}

