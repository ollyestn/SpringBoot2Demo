package com.xyai.shenniuban.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lcz
 * @date 2022/05/15 16:0
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * Header中的Token标志
     */
    private static String LOGIN_SIGN = "Authorization";

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                if (e instanceof AuthorizationException) {
                    throw new AuthorizationException("访问资源权限不足！");
                } else {
                    //token 异常 认证失败
                    throw new AuthenticationException("token 异常 认证失败");
                }
            }
        }
        return true;
    }

    /**
     * 是登录尝试
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        //判断是否是登录请求
        String authorization = req.getHeader(LOGIN_SIGN);
        return authorization != null;
    }

    /**
     * 执行登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(LOGIN_SIGN);
        JWTToken token = new JWTToken(header);
        //提交给realm进⾏登⼊，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        return true;
    }
}
