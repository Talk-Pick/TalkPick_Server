package talkPick.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.global.exception.handler.SecurityExceptionHandler;
import talkPick.global.security.auth.TokenAuthentication;
import talkPick.global.security.constants.AuthConstants;
import talkPick.global.security.jwt.util.JwtProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static talkPick.global.exception.ErrorCode.UNAUTHORIZED;
import static talkPick.global.security.config.WhiteList.PATHS;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    private static final List<AntPathRequestMatcher> WHITE_MATCHERS =
            Arrays.stream(PATHS).map(AntPathRequestMatcher::new).toList();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isWhiteListPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractToken(request);
        if (!jwtProvider.validateToken(accessToken)) {
            throw new SecurityExceptionHandler(UNAUTHORIZED);
        }

        doAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteListPath(HttpServletRequest request) {
        return WHITE_MATCHERS.stream().anyMatch(matcher -> matcher.matches(request));
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith(AuthConstants.BEARER)) {
            throw new SecurityExceptionHandler(UNAUTHORIZED);
        }
        return header.substring(AuthConstants.BEARER.length());
    }

    private void doAuthentication(String token) {
        Long memberId = jwtProvider.getMemberIdFromToken(token);
        String role = jwtProvider.getRoleFromToken(token);
        var authentication = TokenAuthentication.createTokenAuthentication(token, memberId, role);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}