package talkPick.global.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import talkPick.global.security.annotation.UserId;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        var isParamHasUserIdAnnotation = parameter.hasParameterAnnotation(UserId.class);
        var isParamLongType = Long.class.equals(parameter.getParameterType());
        return isParamHasUserIdAnnotation && isParamLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}