package com.xyai.shenniuban.controller;


import com.xyai.shenniuban.config.shiro.JWTUtils;
import com.xyai.shenniuban.entity.User;
import com.xyai.shenniuban.service.UserService;
import com.xyai.shenniuban.utils.R;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lcz
 * @date 2022/05/15 12:42
 * @apiNote
 */
@RestController
public class LoginController {

    @Autowired
    private UserService service;

    @RequestMapping("/login")
    public R login(String userName, String password) {

        if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)) {
            return R.failure(590, "帐号或者密码不能为空");
        }
        User user = service.selectByUserName(userName);
        if (user == null) {
            return R.failure(590, "帐号不存在");
        }
        if (!encrypt(userName, password).equals(user.getPassword())) {
            return R.failure(590, "密码错误");
        }

        //token生成采用加密后的密码，这个和realm中的校验必须一致
        return R.success("token", JWTUtils.sign(userName, user.getPassword()));
    }

    private static String encrypt(String userName, String password) {
        int index = userName.trim().length();
        String salt = userName.trim().charAt(index > 5 ? index - 4 : 0) + "";
        return new Md5Hash(password.trim(), salt).toString();
    }

    public static void main(String[] args) {

        System.out.println(encrypt("admin", "123456"));

    }

}
