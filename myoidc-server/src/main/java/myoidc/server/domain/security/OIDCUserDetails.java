package myoidc.server.domain.security;


import myoidc.server.domain.user.Privilege;
import myoidc.server.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class OIDCUserDetails implements UserDetails {


    protected static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 5161957098952238466L;

    protected User user;

    protected List<GrantedAuthority> authorities = new ArrayList<>();

    public OIDCUserDetails() {
    }

    public OIDCUserDetails(User user) {
        this.user = user;
        initialPrivileges();
    }

    private void initialPrivileges() {
        List<Privilege> privilegeList = privilegeList();
        for (Privilege privilege : privilegeList) {
            this.authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + privilege.name()));
        }
    }

    private List<Privilege> privilegeList() {
        //defaultUser  有所有权限
        if (user.defaultUser()) {
            return Arrays.asList(Privilege.values());
        } else {
            //固定值
            final List<Privilege> privileges = user.privileges();
            privileges.add(Privilege.USER);
            return privileges;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User user() {
        return user;
    }


    @Override
    public String toString() {
        return "{" +
                "user=" + user +
                ", authorities=" + authorities +
                '}';
    }
}