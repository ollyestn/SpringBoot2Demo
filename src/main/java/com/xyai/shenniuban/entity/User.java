package com.xyai.shenniuban.entity;

import lombok.Data;

/**
 * @author lcz
 * @date 2022/05/15 12:38
 */
@Data
public class User {

    // ID
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 盐值
    private String salt;

}
