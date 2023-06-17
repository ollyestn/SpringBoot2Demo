package com.xyai.shenniuban.service.impl;


import com.xyai.shenniuban.entity.Menu;
import com.xyai.shenniuban.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcz
 * @date 2022/05/15 12:47
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public List<Menu> selectPermsByUserId(Integer id) {
        Menu saveUser = new Menu();
        saveUser.setPerms("sys:user:save");

        List<Menu> menus = new ArrayList<>();
        menus.add(saveUser);

        return menus;
    }

}
