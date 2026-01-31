package talkPick.external.google.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.external.google.port.in.GoogleOidcUsecase;
import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.handler.GoogleHandler;

import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleOidcService implements GoogleOidcUsecase {

    // Google JWKS URL
    private static final String JWK_URL = "https://www.googleapis.com/oauth2/v3/certs";
    
    @Value("${google.client-id}")
    private String CLIENT_ID;

    @Override
    public MemberDataDto.MemberData verifyAndParseIdToken(MemberReqDto.OAuth2LoginRequest request) {
        try {
            String idToken = request.getIdToken();
            
            // 1. 헤더 파싱해서 kid 찾기
            String[] parts = idToken.split("\\.");
            if (parts.length != 3) throw new GoogleHandler(ErrorCode.INVALID_JWT_TOKEN);

            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(headerJson);
            String kid = header.get("kid").asText();

            // 2. 구글 공개키 목록(JWKS) 가져와서 kid 일치하는 키 찾기
            JsonNode keys = mapper.readTree(new URL(JWK_URL)).get("keys");
            JsonNode matchedKey = null;
            for (JsonNode key : keys) {
                if (key.get("kid").asText().equals(kid)) {
                    matchedKey = key;
                    break;
                }
            }
            if (matchedKey == null) throw new GoogleHandler(ErrorCode.ERROR_ON_VERIFYING);

            // 3. RSA Public Key 생성
            String n = matchedKey.get("n").asText();
            String e = matchedKey.get("e").asText();
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));
            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);

            // 4. 토큰 검증
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .setAllowedClockSkewSeconds(300)
                    .build();

            Claims claims = parser.parseClaimsJws(idToken).getBody();

            // 5. aud (Client ID) 검증 및 iss 검증
            String aud = claims.getAudience();
            if (aud == null || !aud.equals(CLIENT_ID)) {
                throw new GoogleHandler(ErrorCode.INVALID_JWT_TOKEN);
            }
            
            String iss = claims.getIssuer();
            if (!"https://accounts.google.com".equals(iss) && !"accounts.google.com".equals(iss)) {
                 throw new GoogleHandler(ErrorCode.INVALID_JWT_TOKEN);
            }

            return MemberConverter.toGoogleMemberData(claims);

        } catch (ExpiredJwtException e) {
            throw new GoogleHandler(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (Exception e) {
            log.error("Google OAuth Error", e);
            throw new GoogleHandler(ErrorCode.ERROR_ON_VERIFYING);
        }
    }
}
