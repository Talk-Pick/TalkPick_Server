//package talkPick.rateLimiter.adapter;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import talkPick.batch.topic.port.TopicCacheManager;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class RateLimitControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private TopicCacheManager topicCacheManager;
//
//    @Test
//    @DisplayName("✅ 실제 Controller, 실제 RateLimiterManager 테스트")
//    void 실제_Controller_테스트() throws Exception {
//        String uri = "/test";
//        String ip1 = "127.0.0.1";
//        String ip2 = "127.0.0.2";
//
//        for (int i = 1; i <= 10; i++) {
//            mockMvc.perform(get(uri).with(request -> {
//                        request.setRemoteAddr(ip1);
//                        return request;
//                    }))
//                    .andExpect(status().isOk());
//        }
//
//        mockMvc.perform(get(uri).with(request -> {
//                    request.setRemoteAddr(ip1);
//                    return request;
//                }))
//                .andExpect(status().isTooManyRequests());
//
//        mockMvc.perform(get(uri).with(request -> {
//                    request.setRemoteAddr(ip2);
//                    return request;
//                }))
//                .andExpect(status().isOk());
//    }
//}