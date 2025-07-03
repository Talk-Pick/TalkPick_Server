package talkPick.global.rateLimiter.constants;

public class RateLimiterConstants {
    public static final int BUCKET_CAPACITY = 10;
    public static final int BUCKET_TOKENS = 10;
    public static final int BUCKET_DURATION_MINUTES = 1;
    public static final int BUCKET_MAXIMUM_SIZE = 10_000;
    public static final String BUCKET_KEY = ":";
    public static final int BUCKET_TOKEN_NUM = 1;
}