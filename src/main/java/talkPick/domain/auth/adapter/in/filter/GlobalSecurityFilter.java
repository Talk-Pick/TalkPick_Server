package talkPick.domain.auth.adapter.in.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalSecurityFilter extends OncePerRequestFilter {

    private final List<String> blockedUserAgents;
    private final List<String> blockedPaths;

    public GlobalSecurityFilter(
            @Value("${app.security.blocked-user-agents:}") String blockedUserAgents,
            @Value("${app.security.blocked-paths:}") String blockedPaths) {
        this.blockedUserAgents = parseCommaSeparated(blockedUserAgents);
        this.blockedPaths = parseCommaSeparated(blockedPaths);
    }

    private List<String> parseCommaSeparated(String value) {
        return Optional.ofNullable(value)
                .filter(v -> !v.isBlank())
                .map(v -> Arrays.stream(v.split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .filter(s -> !s.isEmpty())
                        .toList())
                .orElse(List.of());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");

        if (isBlockedUserAgent(userAgent) || isSuspiciousRequest(uri)) {
            log.warn("[Security] Blocked - IP: {}, UA: {}, URI: {}", getClientIp(request), userAgent, uri);
            reject(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .filter(xff -> !xff.isBlank())
                .map(xff -> xff.split(",")[0].trim())
                .orElseGet(request::getRemoteAddr);
    }

    private boolean isBlockedUserAgent(String userAgent) {
        if (blockedUserAgents.isEmpty() || userAgent == null || userAgent.isBlank()) {
            return false;
        }
        String ua = userAgent.toLowerCase();
        return blockedUserAgents.stream().anyMatch(ua::contains);
    }

    private boolean isSuspiciousRequest(String uri) {
        if (uri == null) return false;
        String lowerUri = uri.toLowerCase();
        return lowerUri.contains("..") || lowerUri.contains("//") ||
                blockedPaths.stream().anyMatch(lowerUri::contains);
    }

    private void reject(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write("Access Denied");
    }
}