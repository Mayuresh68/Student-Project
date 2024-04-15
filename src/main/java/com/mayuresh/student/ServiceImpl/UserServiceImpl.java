package com.mayuresh.student.ServiceImpl;

import com.mayuresh.student.Models.Role;
import com.mayuresh.student.Models.User;
import com.mayuresh.student.Repository.RoleRepository;
import com.mayuresh.student.Repository.UserRepository;
import com.mayuresh.student.RequestDTO.UserDto;
import com.mayuresh.student.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public void hello() {
        System.out.println("Hello,Hello");
    }

    @Override
    public User createUser(UserDto userDto) {
        // Create roles
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

// Persist roles
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

// Create user
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Set.of(roleAdmin, roleUser)); // Associate roles with the user

// Persist user
        userRepository.save(user);
        return user;
    }
}
