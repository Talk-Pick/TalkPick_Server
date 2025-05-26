package talkPick.domain.topic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDataDTO {
    private Long id;
    private String title;
    private String detail;
    private String keyword;
    private String thumbnail;
    private String icon;
    private String categoryGroup;
    private String categoryTitle;
    private String categoryDescription;
    private String categoryImageUrl;
    private Integer eCount;
    private Integer iCount;
    private Integer sCount;
    private Integer nCount;
    private Integer fCount;
    private Integer tCount;
    private Integer jCount;
    private Integer pCount;
    private Integer teenCount;
    private Integer twentiesCount;
    private Integer thirtiesCount;
    private Integer fortiesCount;
    private Integer fiftiesCount;
    private Integer maleCount;
    private Integer femaleCount;
}