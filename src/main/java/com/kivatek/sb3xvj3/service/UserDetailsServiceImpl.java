package com.kivatek.sb3xvj3.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.kivatek.sb3xvj3.database.mapper.ds1.UserAccountMapper;
import com.kivatek.sb3xvj3.model.LoginUserDetails;
import com.kivatek.sb3xvj3.model.UserAccount;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public LoginUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserAccount user = userAccountMapper.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No such username");
        }

        List<String> roleList = Lists
                .newArrayList(StringUtils.split(StringUtils.defaultString(user.getRoles()),
                        ','));
        List<GrantedAuthority> authorityList = Lists.newArrayList();
        for (String role : roleList) {
            authorityList.addAll(AuthorityUtils.createAuthorityList(role));
        }

        return new LoginUserDetails(user.getUsername(), user.getPassword(), !user.isDisabledFlag(),
                !user.isExpiredFlag(),
                !user.isCredentialsExpiredFlag(), !user.isLockedFlag(), authorityList);
    }

}
