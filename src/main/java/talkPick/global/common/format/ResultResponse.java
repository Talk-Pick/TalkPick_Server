package talkPick.global.common.format;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class ResultResponse<T> {
    private String status;
    private String message;
    private T data;

    public static<T> ResultResponse<T> success (T data) {
        return new ResultResponse<>("success", "정상 처리되었습니다.", data);
    }

    public static <T> ResultResponse<T> fail (String message) {
        return new ResultResponse<>("fail", message, null);
    }

    public static ResultResponse<Map<String, String>> fail(Map<String, String> errors) {
        return new ResultResponse<>("fail", "입력값이 올바르지 않습니다.", errors);
    }
}