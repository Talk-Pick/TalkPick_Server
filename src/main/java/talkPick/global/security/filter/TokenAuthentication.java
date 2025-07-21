package talkPick.global.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class TokenAuthentication implements Authentication {
    private final String token;
    private final Long memberId;
    private final String role;
    private boolean isAuthenticated = true;

    @Override
    public String getName() {
        return String.valueOf(memberId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Long getPrincipal() {
        return memberId;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public static TokenAuthentication createTokenAuthentication(final String token, final long memberId, final String role) {
        return new TokenAuthentication(token, memberId, role);
    }
}