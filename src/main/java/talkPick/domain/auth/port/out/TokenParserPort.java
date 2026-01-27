package talkPick.domain.auth.port.out;

public interface TokenParserPort {
    boolean validateToken(String token);
    Long getMemberIdFromToken(String token);
    String getRoleFromToken(String token);
    String resolveToken(String bearerHeader);
}