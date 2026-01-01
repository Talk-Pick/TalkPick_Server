package talkPick.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import talkPick.global.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final HttpHeaders jsonHeaders;

    static {
        jsonHeaders = new HttpHeaders();
        jsonHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    // 커스텀 예외 처리
    @ExceptionHandler(TalkPickException.class)
    public ResponseEntity<ApiResponse<Void>> talkPickExceptionHandler(final TalkPickException e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            message = e.getErrorCode().getMessage();
        }
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .headers(jsonHeaders)
                .body(ApiResponse.ofErrorCode(e.getErrorCode(), message));
    }

    // 유효성 검사 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(jsonHeaders)
                .body(ApiResponse.fail(errors));
    }

    // 잘못된 URL 경로 에러 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(jsonHeaders)
                .body(ApiResponse.fail("잘못된 URL입니다."));
    }

    // 기타 서버 내 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(jsonHeaders)
                .body(ApiResponse.fail("[서버 내부 오류] " + e.getMessage()));
    }
}