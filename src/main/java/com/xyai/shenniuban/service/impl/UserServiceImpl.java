package com.xyai.shenniuban.service.impl;


import com.xyai.shenniuban.entity.User;
import com.xyai.shenniuban.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author lcz
 * @date 2022/05/15 12:44
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User selectByUserName(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("dc483e80a7a0bd9ef71d8cf973673924");

        return user;
    }
}
