package talkPick.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import talkPick.domain.type.LoginType;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1284708027L;

    public static final QMember member = new QMember("member1");

    public final talkPick.model.QBaseTime _super = new talkPick.model.QBaseTime(this);

    public final StringPath birth = createString("birth");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final EnumPath<talkPick.domain.type.Gender> gender = createEnum("gender", talkPick.domain.type.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kakaoId = createString("kakaoId");

    public final EnumPath<LoginType> longType = createEnum("longType", LoginType.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<talkPick.model.TalkPickStatus> status = createEnum("status", talkPick.model.TalkPickStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

