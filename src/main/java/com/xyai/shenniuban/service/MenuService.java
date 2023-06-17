package com.xyai.shenniuban.service;



import com.xyai.shenniuban.entity.Menu;

import java.util.List;

/**
 * @author lcz
 * @date 2022/05/15 12:38
 */
public interface MenuService {

    List<Menu> selectPermsByUserId(Integer id);

}
