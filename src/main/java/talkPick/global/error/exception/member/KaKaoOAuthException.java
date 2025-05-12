package talkPick.global.error.exception.member;

public class KaKaoOAuthException extends RuntimeException{
    // 기본 생성자
    public KaKaoOAuthException(String message) {
        super(message);
    }

    public KaKaoOAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
