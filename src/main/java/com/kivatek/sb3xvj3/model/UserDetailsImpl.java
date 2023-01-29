package com.kivatek.sb3xvj3.model;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;

public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private boolean locked;
    private boolean expired;
    private boolean credentialsExpired;
    private boolean deleted;
    private List<UserAuthority> userAuthorities;

    public UserDetailsImpl(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.locked = user.isLocked();
        this.expired = user.isExpired();
        this.credentialsExpired = user.isCredentialsExpired();

        Function<String, UserAuthority> toUserAuthority = role -> {
            return new UserAuthority(role);
        };
        List<String> roleList = Lists
                .newArrayList(StringUtils.split(StringUtils.defaultString(username), ';'));
        userAuthorities = roleList.stream().map(role -> {
            return toUserAuthority.apply(role);
        }).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }

}
