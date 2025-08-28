package talkPick.domain.inquiry.adapter.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import talkPick.domain.inquiry.domain.type.InquiryType;

import java.time.LocalDateTime;

public class InquiryResDto {
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InquiryListItemResDto {
		private Long id;
		private String title;
		private InquiryType type;
		private boolean answered;
		private LocalDateTime createdDate;
	}
}
