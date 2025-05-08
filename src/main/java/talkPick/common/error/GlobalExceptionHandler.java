package talkPick.common.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import talkPick.common.error.exception.TalkPickException;
import talkPick.common.format.ResultResponse;

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
    public ResponseEntity<ResultResponse<String>> talkPickExceptionHandler(final TalkPickException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .headers(jsonHeaders)
                .body(ResultResponse.fail(e.getErrorCode().getMessage()));
    }

    // 유효성 검사 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultResponse<Map<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(jsonHeaders)
                .body(ResultResponse.fail(errors));
    }

    // 기타 서버 내 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse<String>> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(jsonHeaders)
                .body(ResultResponse.fail("[서버 내부 오류] " + e.getMessage()));
    }
}