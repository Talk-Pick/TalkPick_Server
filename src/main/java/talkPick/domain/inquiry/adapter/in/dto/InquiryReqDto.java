package talkPick.domain.inquiry.adapter.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.inquiry.domain.type.InquiryType;

public class InquiryReqDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class inquiryDataRequest {
        @NotNull(message = "문의 종류는 필수입니다.")
        private InquiryType type;

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "문의 내용은 필수 입력 값입니다.")
        @Size(max = 10000, message = "문의 내용은 10000자 이하여야 합니다.")
        private String content;

        @NotBlank(message = "문의 제목은 필수 입력 값입니다.")
        @Size(max = 200, message = "문의 제목은 200자 이하여야 합니다.")
        private String title;
    }
}
