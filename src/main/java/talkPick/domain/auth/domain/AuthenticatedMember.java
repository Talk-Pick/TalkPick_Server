package talkPick.domain.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class AuthenticatedMember implements Authentication {
    private final String token;
    private final Long memberId;
    private final String role;
    private boolean isAuthenticated = true;

    public static AuthenticatedMember of(final String token, final long memberId, final String role) {
        return new AuthenticatedMember(token, memberId, role);
    }

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

    public String getRole() {
        return role;
    }
}