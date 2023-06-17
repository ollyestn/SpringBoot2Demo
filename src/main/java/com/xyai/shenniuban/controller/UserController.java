package com.xyai.shenniuban.controller;


import com.xyai.shenniuban.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lcz
 * @date 2022/05/15 16:55
 */
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("save")
    @RequiresPermissions("sys:user:save")
    public R save() {
        return R.success();
    }

    @RequestMapping("delete")
    @RequiresPermissions("sys:user:delete")
    public R delete() {
        return R.success();
    }

}
