package talkPick.domain.member.adapter.in.dto;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoUserInfo {
    private String id;
    private String connected_at;
    private Map<String, Object> properties;
    private Map<String, Object> kakao_account;

    public void setId(Long id) {
        if (id != null) {
            this.id = String.valueOf(id);
        }
    }

    public void setId(Object id) {
        if (id != null) {
            this.id = String.valueOf(id);
        }
    }

}
