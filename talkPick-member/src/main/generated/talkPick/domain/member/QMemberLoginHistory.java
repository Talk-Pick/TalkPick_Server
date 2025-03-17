package talkPick.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberLoginHistory is a Querydsl query type for MemberLoginHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberLoginHistory extends EntityPathBase<MemberLoginHistory> {

    private static final long serialVersionUID = -1436118320L;

    public static final QMemberLoginHistory memberLoginHistory = new QMemberLoginHistory("memberLoginHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> loginTime = createDateTime("loginTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public QMemberLoginHistory(String variable) {
        super(MemberLoginHistory.class, forVariable(variable));
    }

    public QMemberLoginHistory(Path<? extends MemberLoginHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberLoginHistory(PathMetadata metadata) {
        super(MemberLoginHistory.class, metadata);
    }

}

