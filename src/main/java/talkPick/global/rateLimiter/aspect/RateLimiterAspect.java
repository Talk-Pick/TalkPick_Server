package talkPick.global.rateLimiter.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import talkPick.global.exception.ErrorCode;
import talkPick.global.rateLimiter.exception.RateLimitExceededException;
import talkPick.global.rateLimiter.port.RateLimiterManager;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAspect {
    private final RateLimiterManager rateLimiterManager;

    @Around("@annotation(talkPick.global.rateLimiter.annotation.RateLimited)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if (rateLimiterManager.tryConsume(getIpAddress(request), getUri(request))) {
            return joinPoint.proceed();
        } else {
            throw new RateLimitExceededException(ErrorCode.RATE_LIMIT_EXCEEDED);
        }
    }

    private static String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
    private static String getUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
}