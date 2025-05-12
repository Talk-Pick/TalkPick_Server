package talkPick.domain.member.exception;

public class MemberAlreadyExistsexception extends RuntimeException {
    public MemberAlreadyExistsexception(String message) {
        super(message);
    }
}
