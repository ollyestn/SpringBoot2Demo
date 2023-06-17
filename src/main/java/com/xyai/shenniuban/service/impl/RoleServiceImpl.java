package com.xyai.shenniuban.service.impl;


import com.xyai.shenniuban.entity.Role;
import com.xyai.shenniuban.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcz
 * @date 2022/05/15 13:38
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<Role> selectRoleByUserId(Integer id) {
        List<Role> roles = new ArrayList<>();

        Role admin = new Role();
        admin.setRoleKey("admin");

        roles.add(admin);
        return roles;
    }
}
