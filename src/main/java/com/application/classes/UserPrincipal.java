package com.application.classes;

import com.application.entities.Authority;
import com.application.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) { this.user = user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Authority> authorities = new ArrayList<>();
        this.user.getRoles().stream().forEach( role -> authorities.addAll(role.getAuthorities()));
        return authorities.stream().map( authority -> authority.getName()).map( authorityName ->new SimpleGrantedAuthority(authorityName)).collect(Collectors.toList());

    }

    @Override
    public String getPassword() { return this.user.getPassword(); }
    @Override
    public String getUsername() { return this.user.getUsername(); }
    @Override
    public boolean isAccountNonExpired() { return true;  }
    @Override
    public boolean isAccountNonLocked() {  return this.user.isNotLocked(); }
    @Override
    public boolean isCredentialsNonExpired() {return true; }
    @Override
    public boolean isEnabled() { return this.user.isActive(); }

}
