package com.mayuresh.student.security;

import com.mayuresh.student.Models.Role;
import com.mayuresh.student.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;



public class UserInfoUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
//    private String name;
//    private String password;
//    private List<GrantedAuthority> authorities;
//    private Set<Role> roles;

    private User user;


    public UserInfoUserDetails(User userInfo) {
        System.out.println("UserInfoUserDetails...Constructor calling");
        this.user = userInfo;
//        name = userInfo.getUsername();
//        password = userInfo.getPassword();
//        roles = userInfo.getRoles();
//        authorities = userInfo.getRoles().stream()
//                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
