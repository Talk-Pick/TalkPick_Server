package talkPick.batch.topic.exception;

public class KaKaoOAuthException extends RuntimeException{
    // 기본 생성자
    public KaKaoOAuthException(String message) {
        super(message);
    }

    public KaKaoOAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
