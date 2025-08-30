package talkPick.domain.topic.domain.type;

import lombok.Getter;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public enum Keyword {
    VALUES("https://cdn.talkpick.com/keyword/values.png", "https://cdn.talkpick.com/icon/values.png"),
    WHAT_IF("https://cdn.talkpick.com/keyword/what_if.png", "https://cdn.talkpick.com/icon/what_if.png"),
    PERSONALITY("https://cdn.talkpick.com/keyword/personality.png", "https://cdn.talkpick.com/icon/personality.png"),
    HOBBY("https://cdn.talkpick.com/keyword/hobby.png", "https://cdn.talkpick.com/icon/hobby.png"),
    GAME("https://cdn.talkpick.com/keyword/game.png", "https://cdn.talkpick.com/icon/game.png"),
    TRAVEL("https://cdn.talkpick.com/keyword/travel.png", "https://cdn.talkpick.com/icon/travel.png"),
    TASTE("https://cdn.talkpick.com/keyword/taste.png", "https://cdn.talkpick.com/icon/taste.png"),
    DAILY_LIFE("https://cdn.talkpick.com/keyword/daily_life.png", "https://cdn.talkpick.com/icon/daily_life.png"),
    RELATIONSHIP("https://cdn.talkpick.com/keyword/relationship.png", "https://cdn.talkpick.com/icon/relationship.png"),
    MEMORY("https://cdn.talkpick.com/keyword/memory.png", "https://cdn.talkpick.com/icon/memory.png"),
    SOCIETY_TREND("https://cdn.talkpick.com/keyword/society_trend.png", "https://cdn.talkpick.com/icon/society_trend.png"),
    VERSUS("https://cdn.talkpick.com/keyword/versus.png", "https://cdn.talkpick.com/icon/versus.png");

    private final String imageUrl;
    private final String iconUrl;

    Keyword(String imageUrl, String iconUrl) {
        this.imageUrl = imageUrl;
        this.iconUrl = iconUrl;
    }

    public static Keyword getRandom() {
        Keyword[] values = Keyword.values();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}