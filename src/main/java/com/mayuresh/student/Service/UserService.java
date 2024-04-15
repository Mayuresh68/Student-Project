package com.mayuresh.student.Service;

import com.mayuresh.student.Models.User;
import com.mayuresh.student.RequestDTO.UserDto;

public interface UserService {

    public void hello();

    User createUser(UserDto userDto);
}
