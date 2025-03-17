package talkPick.domain.profile;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = -1608496837L;

    public static final QProfile profile = new QProfile("profile");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<talkPick.domain.type.MBTI> mbti = createEnum("mbti", talkPick.domain.type.MBTI.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QProfile(String variable) {
        super(Profile.class, forVariable(variable));
    }

    public QProfile(Path<? extends Profile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfile(PathMetadata metadata) {
        super(Profile.class, metadata);
    }

}

