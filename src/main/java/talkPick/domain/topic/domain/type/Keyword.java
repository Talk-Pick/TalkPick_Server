package talkPick.domain.topic.domain.type;

import java.util.concurrent.ThreadLocalRandom;

public enum Keyword {
    VALUES, WHAT_IF, PERSONALITY, HOBBY, GAME, TRAVEL, TASTE, DAILY_LIFE, RELATIONSHIP, MEMORY, SOCIETY_TREND, VERSUS;

    public static Keyword getRandom() {
        Keyword[] values = Keyword.values();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}