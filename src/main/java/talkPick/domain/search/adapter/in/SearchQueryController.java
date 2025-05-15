package talkPick.domain.search.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.search.adapter.out.dto.SearchResDTO;
import talkPick.domain.search.port.in.SearchQueryUseCase;
import talkPick.global.common.model.PageCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchQueryController implements SearchQueryApi {
    private final SearchQueryUseCase searchQueryUseCase;

    @Override
    @GetMapping("/")
    public PageCustom<SearchResDTO.Topic> searchTopics(@RequestParam(required = false) String category, Pageable pageable) {
        return searchQueryUseCase.search(category, pageable);
    }
}