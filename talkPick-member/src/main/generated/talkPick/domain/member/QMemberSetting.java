package talkPick.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberSetting is a Querydsl query type for MemberSetting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberSetting extends EntityPathBase<MemberSetting> {

    private static final long serialVersionUID = -1506560693L;

    public static final QMemberSetting memberSetting = new QMemberSetting("memberSetting");

    public final BooleanPath ageTermAgree = createBoolean("ageTermAgree");

    public final DateTimePath<java.time.LocalDateTime> ageTermAgreeDate = createDateTime("ageTermAgreeDate", java.time.LocalDateTime.class);

    public final BooleanPath eventEmailPush = createBoolean("eventEmailPush");

    public final DateTimePath<java.time.LocalDateTime> eventEmailPushDate = createDateTime("eventEmailPushDate", java.time.LocalDateTime.class);

    public final BooleanPath eventPush = createBoolean("eventPush");

    public final DateTimePath<java.time.LocalDateTime> eventPushDate = createDateTime("eventPushDate", java.time.LocalDateTime.class);

    public final BooleanPath eventSmsPush = createBoolean("eventSmsPush");

    public final DateTimePath<java.time.LocalDateTime> eventSmsPushDate = createDateTime("eventSmsPushDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath marketingTermAgree = createBoolean("marketingTermAgree");

    public final DateTimePath<java.time.LocalDateTime> marketingTermAgreeDate = createDateTime("marketingTermAgreeDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final BooleanPath privateTermAgree = createBoolean("privateTermAgree");

    public final DateTimePath<java.time.LocalDateTime> privateTermAgreeDate = createDateTime("privateTermAgreeDate", java.time.LocalDateTime.class);

    public final BooleanPath serviceTermAgree = createBoolean("serviceTermAgree");

    public final DateTimePath<java.time.LocalDateTime> serviceTermAgreeDate = createDateTime("serviceTermAgreeDate", java.time.LocalDateTime.class);

    public QMemberSetting(String variable) {
        super(MemberSetting.class, forVariable(variable));
    }

    public QMemberSetting(Path<? extends MemberSetting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberSetting(PathMetadata metadata) {
        super(MemberSetting.class, metadata);
    }

}

