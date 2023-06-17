package com.xyai.shenniuban.service;



import com.xyai.shenniuban.entity.Role;

import java.util.List;

/**
 * @author lcz
 * @date 2022/05/15 12:37
 */
public interface RoleService {

    List<Role> selectRoleByUserId(Integer id);

}
