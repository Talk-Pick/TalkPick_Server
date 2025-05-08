package talkPick.common.error;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import talkPick.common.error.exception.TalkPickException;
import talkPick.common.format.ResultResponse;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    // 해당 Advice 적용 범위
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    // 응답 변환 매서드
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // Swagger API 요청은 변환 X
        String requestPath = request.getURI().getPath();
        if (requestPath.startsWith("/v3/api-docs") || requestPath.startsWith("/swagger-ui")) {
            return body;
        }

        // 응답이 byte[]인 경우 변환 X
        if (body instanceof byte[]) {
            return body;
        }

        // 반환이 void이면 data 없이 응답
        if (Void.TYPE.equals(returnType.getParameterType())) {
            return new ResultResponse<>("success", "처리가 완료되었습니다.", null);
        }

        // 반환이 String인 경우 예외 발생
        if (body instanceof String) {
            throw new TalkPickException(ErrorCode.NOT_ALLOW_STRING);
        }

        if(body instanceof ResultResponse){
            return body;
        }
        return ResultResponse.success(body);
    }
}