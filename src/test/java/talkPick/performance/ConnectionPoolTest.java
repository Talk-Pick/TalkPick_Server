package talkPick.performance;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Import(PerformanceTestService.class)
public class ConnectionPoolTest {

    private static final Logger log = LoggerFactory.getLogger(ConnectionPoolTest.class);

    @Autowired
    private PerformanceTestService performanceTestService;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("커넥션 풀 사이즈와 컨텍스트 스위칭 성능 테스트")
    void testConnectionPoolPerformance() throws InterruptedException {
        // 1. 현재 HikariCP 설정 확인
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
        int currentPoolSize = hikariDataSource.getMaximumPoolSize();
        log.info("==================================================");
        log.info("현재 HikariCP Maximum Pool Size: {}", currentPoolSize);
        log.info("==================================================");

        // 테스트 설정
        int threadCount = 3000; // 동시 요청 스레드 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        StopWatch stopWatch = new StopWatch();

        log.info("테스트 시작: {}개의 동시 요청 실행 중...", threadCount);
        stopWatch.start();

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    performanceTestService.heavyWork();
                } catch (Exception e) {
                    log.error("테스트 중 예외 발생", e);
                } finally {
                    latch.countDown();
                }
            });
        }

        // 모든 스레드가 끝날 때까지 대기
        latch.await();
        stopWatch.stop();

        log.info("==================================================");
        log.info("테스트 완료");
        log.info("총 소요 시간: {} ms", stopWatch.getTotalTimeMillis());
        log.info("초당 처리량(TPS): {}", threadCount / stopWatch.getTotalTimeSeconds());
        log.info("==================================================");
        
        /* 
         * [테스트 가이드]
         * 1. application.yml 또는 환경변수에서 'maximum-pool-size'를 10으로 설정 후 실행해 보세요.
         * 2. 그 다음, 200으로 설정 후 실행해 보세요.
         * 
         * 예상 결과:
         * - Pool Size 10 (적절): 스레드들이 줄을 서서(Queueing) 기다리지만, CPU는 해시 연산에 집중하므로 전체 처리 속도는 빠릅니다.
         * - Pool Size 200 (과다): 200개의 스레드가 동시에 커넥션을 잡고 CPU 쟁탈전을 벌입니다. 
         *   컨텍스트 스위칭 비용으로 인해 총 소요 시간이 오히려 더 늘어날 수 있습니다.
         */
    }
}
