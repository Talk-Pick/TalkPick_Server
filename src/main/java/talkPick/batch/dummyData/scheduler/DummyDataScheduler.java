package talkPick.batch.dummyData.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import talkPick.batch.dummyData.dto.CategoryReqDTO;
import talkPick.domain.topic.adapter.out.repository.*;
import talkPick.domain.topic.domain.*;
import talkPick.domain.topic.domain.type.CategoryGroup;
import talkPick.domain.topic.domain.type.Keyword;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class DummyDataScheduler {
    private final CategoryJpaRepository categoryJpaRepository;
    private final TopicJpaRepository topicJpaRepository;
    private final TopicStatJpaRepository topicStatJpaRepository;
    private final TopicImageJpaRepository topicImageJpaRepository;
    private final TopicKeywordRepository topicKeywordRepository;

    private static final List<CategoryReqDTO.Create> FIXED_CATEGORIES = List.of(
            CategoryReqDTO.Create.of("소개팅/과팅", "처음 만나는 이성과의 대화", "https://dummyimage.com/600x400/000/fff&text=소개팅", CategoryGroup.STRANGER),
            CategoryReqDTO.Create.of("그룹 첫 모임", "처음 모인 그룹에서 어색함을 깨는 질문", "https://dummyimage.com/600x400/111/fff&text=그룹모임", CategoryGroup.STRANGER),
            CategoryReqDTO.Create.of("룸메 첫 만남", "함께 살아갈 룸메이트와의 첫 대화", "https://dummyimage.com/600x400/222/fff&text=룸메", CategoryGroup.STRANGER),
            CategoryReqDTO.Create.of("기타/아이스브레이킹", "어색한 분위기를 깨는 아무말 대잔치", "https://dummyimage.com/600x400/333/fff&text=기타", CategoryGroup.STRANGER),
            CategoryReqDTO.Create.of("가족", "가족끼리도 서로를 더 알아가기 위한 질문", "https://dummyimage.com/600x400/444/fff&text=가족", CategoryGroup.CLOSE),
            CategoryReqDTO.Create.of("친구", "친구 사이에도 새로운 면을 발견할 수 있는 질문", "https://dummyimage.com/600x400/555/fff&text=친구", CategoryGroup.CLOSE),
            CategoryReqDTO.Create.of("연인", "사랑하는 사람과 더 깊은 이야기를 나누기 위한 질문", "https://dummyimage.com/600x400/666/fff&text=연인", CategoryGroup.CLOSE),
            CategoryReqDTO.Create.of("동료", "직장 동료, 팀원들과 알아가며 친해지는 시간", "https://dummyimage.com/600x400/777/fff&text=동료", CategoryGroup.CLOSE)
    );

    @PostConstruct
    public void generateDummyData() {
        //TODO Member 더미 데이터 + JWT 토큰 만들어야 함.

        List<Category> categories = saveCategories();

        for (int i = 0; i < 40; i++) {
            Category randomCategory = categories.get(ThreadLocalRandom.current().nextInt(categories.size()));
            Keyword randomKeyword = Keyword.getRandom();

            Topic topic = saveTopic(i, randomCategory);

            TopicStat topicStat = saveTopicStat(topic);

            saveTopicImage(topicStat, i);

            saveTopicKeyword(topic, randomKeyword);
        }

    }

    private List<Category> saveCategories() {
        if (categoryJpaRepository.count() == 0) {
            for (CategoryReqDTO.Create categoryDto : FIXED_CATEGORIES) {
                Category category = Category.of(
                        categoryDto.title(),
                        categoryDto.description(),
                        categoryDto.imageUrl(),
                        categoryDto.categoryGroup()
                );
                categoryJpaRepository.save(category);
            }
        }

        return categoryJpaRepository.findAll();
    }

    private Topic saveTopic(int i, Category randomCategory) {
        Topic topic = Topic.of("토픽 더미 Title " + (i +1), "토픽 더미 Detail " + (i +1),"Thumbnail ", "icon", randomCategory.getId(), 333L);
        topicJpaRepository.save(topic);
        return topic;
    }

    private TopicStat saveTopicStat(Topic topic) {
        TopicStat topicStat = TopicStat.of(topic.getId());
        topicStatJpaRepository.save(topicStat);
        return topicStat;
    }

    private void saveTopicImage(TopicStat topicStat, int i) {
        TopicImage topicImage = TopicImage.of(topicStat.getId(), "https://dummyimage.com/600x400/888/fff&text=" + i);
        topicImageJpaRepository.save(topicImage);
    }

    private void saveTopicKeyword(Topic topic, Keyword randomKeyword) {
        TopicKeyword topicKeyword = TopicKeyword.of(topic.getId(), randomKeyword);
        topicKeywordRepository.save(topicKeyword);
    }

}