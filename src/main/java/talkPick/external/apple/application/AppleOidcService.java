package talkPick.external.apple.application;

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
import talkPick.external.apple.port.in.AppleOidcUsecase;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.AppleHandler;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOidcService implements AppleOidcUsecase {

    // Apple JWKS
    private static final String JWK_URL = "https://appleid.apple.com/auth/keys";
    // iss 검증 값
    private static final String ISSUER = "https://appleid.apple.com";
    @Value("${apple.bundle-id}")
    private String BUNDLE_ID;

    @Override
    public MemberDataDto.MemberData verifyAndParseIdToken(MemberReqDto.OAuth2LoginRequest request) {
        try {
            String identityToken = request.getIdToken();
            String[] parts = identityToken.split("\\.");
            if (parts.length != 3) throw new AppleHandler(ErrorCode.INVALID_JWT_TOKEN);

            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(headerJson);
            String kid = header.get("kid").asText();


            JsonNode keys = mapper.readTree(new URL(JWK_URL)).get("keys");
            JsonNode matchedKey = null;
            for (JsonNode key : keys) {
                if (key.get("kid").asText().equals(kid)) {
                    matchedKey = key;
                    break;
                }
            }
            if (matchedKey == null) throw new AppleHandler(ErrorCode.ERROR_ON_VERIFYING);


            String n = matchedKey.get("n").asText();
            String e = matchedKey.get("e").asText();
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));
            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);


            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .requireIssuer(ISSUER)
                    .setAllowedClockSkewSeconds(300)
                    .build();

            Claims claims = parser.parseClaimsJws(identityToken).getBody();

            Object audObj = claims.get("aud");
            boolean audOk = false;
            if (audObj instanceof String audStr) {
                audOk = BUNDLE_ID.equals(audStr);
            } else if (audObj instanceof List<?> audList) {
                audOk = audList.stream().anyMatch(a -> BUNDLE_ID.equals(String.valueOf(a)));
            }
            if (!audOk) throw new AppleHandler(ErrorCode.INVALID_JWT_TOKEN);

            return MemberConverter.toAppleMemberData(claims);

        } catch (ExpiredJwtException e) {
            throw new AppleHandler(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (Exception e) {
            log.error("Apple OAuth Error", e);
            throw new AppleHandler(ErrorCode.ERROR_ON_VERIFYING);
        }
    }
}