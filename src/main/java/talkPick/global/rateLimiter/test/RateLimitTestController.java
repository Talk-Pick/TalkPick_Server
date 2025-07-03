package talkPick.global.rateLimiter.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.global.rateLimiter.annotation.RateLimited;

@RestController
@RequestMapping("/test")
public class RateLimitTestController {

    @GetMapping
    @RateLimited
    public void test() {
    }
}