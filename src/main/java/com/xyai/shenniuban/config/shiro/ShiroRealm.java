package com.xyai.shenniuban.config.shiro;


import com.xyai.shenniuban.entity.Menu;
import com.xyai.shenniuban.entity.Role;
import com.xyai.shenniuban.entity.User;
import com.xyai.shenniuban.service.MenuService;
import com.xyai.shenniuban.service.RoleService;
import com.xyai.shenniuban.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author lcz
 * @date 2022/05/15 15:50:50
 * @apiNote 用户登录时认证和授权
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    /**
     * 因为是自己定义的Token，shiro无法识别，需要修改Realm中的supports方法，使 shiro 支持自定义token。
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证:校验帐号和密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        //从token中获取用户名eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODc2MDYwMzUsInVzZXJuYW1lIjoiYWRtaW4ifQ.CLu_cnnggmX9n7YyV-ofiZZ0vhssoXsTqR0i-mf36RA
        String username = JWTUtils.getUsername(token);
        //获取数据库中存取的用户，密码是加密后的
        User user = userService.selectByUserName(username);
        if (user != null) {
            // 密码验证
            if (!JWTUtils.verify(token, username, user.getPassword())) {
                // 密码不正确
                throw new IncorrectCredentialsException();
            }
            return new SimpleAuthenticationInfo(token, token, getName());
        } else {
            throw new UnknownAccountException();
        }
    }

    /**
     * 授权:授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户名
        String userName = JWTUtils.getUsername(principals.toString());
        //根据用户名查询用户
        User user = userService.selectByUserName(userName);
        //实例化一个授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user != null) {
            //赋予角色
            List<Role> roles = roleService.selectRoleByUserId(user.getId());
            for (Role role : roles) {
                //将角色添加到授权信息中
                info.addRole(role.getRoleKey());
            }
            //赋予资源
            List<Menu> permissions = menuService.selectPermsByUserId(user.getId());
            for (Menu permission : permissions) {
                //将权限添加授权信息中
                info.addStringPermission(permission.getPerms());
            }
        }
        return info;
    }

}