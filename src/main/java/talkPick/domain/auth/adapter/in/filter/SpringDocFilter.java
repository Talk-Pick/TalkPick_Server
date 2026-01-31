package talkPick.domain.auth.adapter.in.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class SpringDocFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER = "X-Swagger-Key";
    private static final String AUTH_PARAM = "key";
    private final String swaggerPassword;

    public SpringDocFilter(@Value("${app.security.swagger-password:}") String swaggerPassword) {
        this.swaggerPassword = swaggerPassword;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (!isSwaggerPath(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (swaggerPassword == null || swaggerPassword.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        String headerKey = request.getHeader(AUTH_HEADER);
        String paramKey = request.getParameter(AUTH_PARAM);

        if (swaggerPassword.equals(headerKey) || swaggerPassword.equals(paramKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.warn("[Swagger] 비정상 접근 시도 - IP: {}, URI: {}", request.getRemoteAddr(), uri);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Swagger access requires authentication. Add ?key=<password> or X-Swagger-Key header.");
    }

    private boolean isSwaggerPath(String uri) {
        return uri.contains("temp-dev-secret-ui") || uri.contains("temp-dev-secret-docs");
    }
}