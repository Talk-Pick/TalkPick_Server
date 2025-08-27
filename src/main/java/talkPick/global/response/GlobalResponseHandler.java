package talkPick.global.response;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.TalkPickException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // Swagger API 요청은 변환하지 않음
        String requestPath = request.getURI().getPath();
        if (requestPath.startsWith("/v3/api-docs") || requestPath.startsWith("/swagger-ui")) {
            return body;
        }

        // byte[]는 변환하지 않음
        if (body instanceof byte[]) {
            return body;
        }

        // 반환 타입이 void면 data 없이 success
        if (Void.TYPE.equals(returnType.getParameterType())) {
            return ApiResponse.success(null);
        }

        // 반환이 String이면 예외
        if (body instanceof String) {
            throw new TalkPickException(ErrorCode.NOT_ALLOW_STRING);
        }

        // 이미 ApiResponse인 경우 그대로 반환 → 중요!
        if (body instanceof ApiResponse) {
            return body;
        }

        // 일반 객체는 성공 응답으로 감싸기
        return ApiResponse.success(body);
    }
}