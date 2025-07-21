package talkPick.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.global.exception.ErrorCode;
import talkPick.global.security.constants.AuthConstants;
import talkPick.global.security.exception.UnauthorizedException;
import talkPick.global.security.jwt.util.JwtProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static talkPick.global.security.model.WhiteList.PATHS;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    private static final List<AntPathRequestMatcher> whiteMatchers =
            Arrays.stream(PATHS).map(AntPathRequestMatcher::new).toList();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (whiteMatchers.stream().anyMatch(matcher -> matcher.matches(request))) {
            filterChain.doFilter(request, response);
            return;
        }
        final var accessToken = getAccessToken(request);
        final var memberId = jwtProvider.getMemberIdFromToken(accessToken);
        final var role = jwtProvider.getRoleFromToken(accessToken);
        doAuthentication(accessToken, memberId, role);
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(final HttpServletRequest request) {
        final var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(AuthConstants.BEARER)) {
            return accessToken.substring(AuthConstants.BEARER.length());
        }
        throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }

    private void doAuthentication(final String token, final Long memberId, final String role) {
        var tokenAuthentication = TokenAuthentication.createTokenAuthentication(token, memberId, role);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(tokenAuthentication);
    }
}