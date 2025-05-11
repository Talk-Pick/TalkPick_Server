package talkPick.common.security.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import talkPick.domain.admin.Admin;
import talkPick.domain.auth.Role;
import talkPick.domain.member.Member;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;
    @Getter
    private final String name;
    private final String password;
    @Getter
    private final Role role;

    public CustomUserDetails(Admin admin) {
        this.id = admin.getId();
        this.password = admin.getAuthInfo().getPassword();
        this.name = admin.getName();
        this.role = admin.getRole();
    }

    public CustomUserDetails(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.role = member.getMemberRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> "ROLE_" + role);
        return collection;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
