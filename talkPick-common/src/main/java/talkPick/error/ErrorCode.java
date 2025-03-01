package talkPick.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import talkPick.config.JasyptConfig;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 회원입니다."),

    // Admin
    ADMIN_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자 이메일 정보가 존재하지 않습니다."),
    ADMIN_PASSWORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "관리자 비밀번호 정보가 존재하지 않습니다."),
    ADMIN_LOGIN_PASSWORD_FAULT(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

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
    JASYPT_KEY_CONFIGURATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "jayspt 암호화 키가 유효하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
