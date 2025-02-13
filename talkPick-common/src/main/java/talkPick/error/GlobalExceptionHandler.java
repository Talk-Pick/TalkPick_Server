package talkPick.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import talkPick.error.exception.TalkPickException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TalkPickException.class)
    public ResponseEntity<ErrorResponse> ticTocExceptionHandler(final TalkPickException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorResponse.of(e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        response.put("errorStatus", HttpStatus.BAD_REQUEST);
        response.put("message", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}