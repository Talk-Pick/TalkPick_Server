package talkPick;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.security.Security;

@EnableRetry
@EnableScheduling
@SpringBootApplication
public class TalkPickApplication {
    public static void main(String[] args) {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        SpringApplication.run(TalkPickApplication.class, args
        );
    }
}