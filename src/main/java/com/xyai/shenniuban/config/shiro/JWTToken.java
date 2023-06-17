package com.xyai.shenniuban.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lcz
 * @date 2022/05/15 15:52
 */
public class JWTToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
