package talkPick.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Topic
    TOPIC_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 토픽입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 카테고리입니다."),
    KEYWORD_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 키워드입니다."),
    ADD_LIKE_FAIL(HttpStatus.BAD_REQUEST,"토픽 좋아요 실패했습니다."),
    DUPLICATE_LIKE(HttpStatus.BAD_REQUEST,"이미 좋아요 눌렀습니다."),

    // Random
    TOPIC_STAT_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 토픽 통계입니다."),
    RANDOM_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 랜덤입니다."),
    RANDOM_TOPIC_HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 랜덤 토픽 기록입니다."),

    // Admin
    ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자 정보를 찾을 수 없습니다."),
    ADMIN_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 이메일로 가입된 관리자가 이미 존재합니다."),
    ADMIN_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자 이메일 정보가 존재하지 않습니다."),
    ADMIN_PASSWORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자 비밀번호 정보가 존재하지 않습니다."),
    ADMIN_LOGIN_PASSWORD_FAULT(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    ADMIN_PASSWORD_NOT_ALLOWED(HttpStatus.BAD_REQUEST,"비밀번호가 보안 정책을 만족하지 않습니다."),

    // Notice
    NOTICE_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 공지사항 입니다."),

    // Server
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    UNSUPPORTED_TOKEN_TYPE(HttpStatus.UNAUTHORIZED,  "잘못된 토큰 형식입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED,  "잘못된 토큰 구조입니다."),
    INVALID_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰 서명입니다."),
    TOKEN_SUBJECT_NOT_NUMERIC_STRING(HttpStatus.UNAUTHORIZED, "토큰의 subject가 숫자 문자열이 아닙니다."),
    NOT_ALLOW_STRING(HttpStatus.INTERNAL_SERVER_ERROR ,"잘못된 String 반환입니다. 서버 담당자에게 문의하세요."),

    // Common
    JASYPT_KEY_CONFIGURATION_ERROR(HttpStatus.BAD_REQUEST, "jayspt 암호화 키가 유효하지 않습니다."),

    // Auth
    INVALID_ROLE(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 role입니다."),

    // LLM
    LLM_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "LLM 서버 요청 또는 응답 처리에 실패했습니다."),

    // JVM
    JVM_CACHE_REFRESH_FAILED(HttpStatus.BAD_REQUEST, "JVM 캐시 Refresh 중 예외가 발생했습니다."),

    // RateLimit
    RATE_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "사용자 요청이 너무 많습니다."),

    // Health Check
    DB_HEALTH_CHECK_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "DB 연결 실패 에러입니다."),
    URL_HEALTH_CHECK_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "URL 연결 실패 에러입니다."),

    // Role
    ROLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "Role이 존재하지 않습니다."),

    // JWT
    INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "만료된 JWT 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다. 재로그인이 필요합니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "만료된 리프레시 토큰입니다. 재로그인이 필요합니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    MEMBER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    INVALID_MEMBER_INFO(HttpStatus.BAD_REQUEST, "회원 필수 정보가 누락되었습니다."),
    PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호는 필수입니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 8~20자이며 대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // Term
    TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 약관입니다."),
    REQUIRED_TERM_NOT_AGREED(HttpStatus.BAD_REQUEST, "필수 약관에 동의하지 않았습니다."),

    // Kakao
    INVALID_ID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 ID Token 입니다."),
    ERROR_ON_VERIFYING(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 토큰 검증 도중 에러 발생"),
    
    ;

    private final HttpStatus status;
    private final String message;
}
