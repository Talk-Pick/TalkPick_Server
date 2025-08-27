package talkPick.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message("정상 처리되었습니다.")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static ApiResponse<Void> fail(String message) {
        return ApiResponse.<Void>builder()
                .status("fail")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<Map<String, String>> fail(Map<String, String> errors) {
        return ApiResponse.<Map<String, String>>builder()
                .status("fail")
                .message("입력값이 올바르지 않습니다.")
                .timestamp(LocalDateTime.now())
                .data(errors)
                .build();
    }

    public static ApiResponse<Void> ofErrorCode(ErrorCode errorCode) {
        return ApiResponse.<Void>builder()
                .status("fail")
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> ofErrorCode(ErrorCode errorCode, T data) {
        return ApiResponse.<T>builder()
                .status("fail")
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
}
