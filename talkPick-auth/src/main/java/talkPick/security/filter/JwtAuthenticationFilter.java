package talkPick.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var accessToken = getAccessToken(request);
        final var userId = jwtProvider.getUserIdFromToken(accessToken);
        doAuthentication(accessToken, userId);
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(final HttpServletRequest request) {
        final var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(AuthConstants.BEARER)) {
            return accessToken.substring(AuthConstants.BEARER.length());
        }
        throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }

    private void doAuthentication(final String token, final Long userId) {
        var tokenAuthentication = TokenAuthentication.createTokenAuthentication(token, userId);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(tokenAuthentication);
    }
}
