package talkPick.common.error;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private HttpStatus errorStatus;
    private String message;

    public static ErrorResponse of(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorStatus(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }
}