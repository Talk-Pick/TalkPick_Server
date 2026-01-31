package talkPick.domain.inquiry.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.inquiry.adapter.out.dto.InquiryResDto;
import talkPick.domain.inquiry.port.in.InquiryQueryUseCase;
import talkPick.domain.inquiry.port.out.InquiryQueryRepositoryPort;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.member.domain.Member;
import talkPick.core.common.response.CursorPageResponse;
import talkPick.domain.auth.port.out.TokenParserPort;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryQueryService implements InquiryQueryUseCase {
    private final MemberQueryRepositoryPort memberQueryRepositoryPort;
    private final InquiryQueryRepositoryPort inquiryQueryRepositoryPort;
    private final TokenParserPort tokenParserPort;

    @Override
    public CursorPageResponse<InquiryResDto.InquiryListItemResDto> getMyInquiries(String authorization, LocalDateTime cursor, int size) {
        Long memberId = tokenParserPort.getMemberIdFromToken(tokenParserPort.resolveToken(authorization));

        Member findMember = memberQueryRepositoryPort.findMemberById(memberId);
        // size + 1개 조회하여 다음 페이지 존재 여부 판단
        List<InquiryResDto.InquiryListItemResDto> items = inquiryQueryRepositoryPort.findMyInquiries(findMember, cursor, size + 1);

        // 다음 페이지 존재 시 마지막 데이터 제거
        boolean hasNext = items.size() > size;
        if (hasNext) items.remove(items.size() - 1);

        // 다음 페이지 조회용 커서 생성
        CursorPageResponse.Cursor nextCursor = null;
        if (hasNext && !items.isEmpty()) {
            InquiryResDto.InquiryListItemResDto last = items.get(items.size() - 1);
            nextCursor = new CursorPageResponse.Cursor(last.getCreatedDate(), last.getId());
        }

        // 커서 기반 페이징 응답 반환
        return CursorPageResponse.<InquiryResDto.InquiryListItemResDto>builder()
                .items(items)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }
}
