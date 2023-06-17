package com.xyai.shenniuban.service;


import com.xyai.shenniuban.entity.User;

/**
 * @author lcz
 * @date 2022/05/15 12:37
 */
public interface UserService {

    User selectByUserName(String username);

}
