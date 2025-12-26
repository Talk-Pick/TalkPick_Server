package talkPick.performance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PerformanceTestService {

    /**
     * DB 커넥션을 점유한 상태(@Transactional)에서
     * CPU 연산을 수행하여 컨텍스트 스위칭 부하를 시뮬레이션합니다.
     */
    @Transactional
    public void heavyWork() {
        try {
            // CPU 부하를 주기 위한 해시 계산 (50,000번 반복)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = "dummy-data-for-simulation-" + System.nanoTime();
            final Object SHARED_LOCK = new Object();
            
            for (int i = 0; i < 50000; i++) {
                digest.update(data.getBytes());
                digest.digest();

                synchronized (SHARED_LOCK) {
                    try {
                        // 아주 짧은 락 점유
                        Thread.sleep(1);
                    } catch (InterruptedException e) {}
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
        }
    }
}
