package com.mayuresh.student.security;

import com.mayuresh.student.Models.User;
import com.mayuresh.student.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername....!! username"+": :"+username);
        User userInfo = userRepository.findByUsername(username);
//        System.out.println("userinfo"+" "+ userInfo.get().getUsername()+"..."+ userInfo.get().getPassword());
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

        if (userInfo == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserInfoUserDetails(userInfo);
    }
}
