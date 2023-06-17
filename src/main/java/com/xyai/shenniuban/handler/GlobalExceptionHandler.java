package com.xyai.shenniuban.handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

/**
 * @author lcz
 * @date 2022/05/15 16:09
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Object errorHandler(Exception e, HttpServletRequest httpServletRequest) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        if (e instanceof NoHandlerFoundException) {
            jsonObject.put("code", 404);
            jsonObject.put("msg", "找不到请求资源");
        } else if (e instanceof MissingServletRequestParameterException) {
            jsonObject.put("code", -200);
            jsonObject.put("msg", "缺少参数");
        } else if (e instanceof UnauthenticatedException) {
            jsonObject.put("code", 401);
            jsonObject.put("msg", "用户未登录,请登录");
        } else if (e instanceof AuthorizationException) {
            jsonObject.put("code", 402);
            jsonObject.put("msg", "权限不足");
        } else if (e instanceof AuthenticationException) {
            jsonObject.put("code", 403);
            jsonObject.put("msg", "帐号密码错误,请重新登录");
        } else if (e instanceof MaxUploadSizeExceededException) {
            jsonObject.put("code", 240);
            jsonObject.put("msg", "文件上传超出大小限制");
        } else if (e instanceof SQLException) {
            jsonObject.put("code", 250);
            jsonObject.put("msg", "数据库操作失败");
        } else if (e instanceof SocketTimeoutException) {
            jsonObject.put("code", 260);
            jsonObject.put("msg", "服务连接超时");
        } else if (e instanceof SocketException) {
            jsonObject.put("code", 240);
            jsonObject.put("msg", "服务连接失败");
        } else if (e instanceof IOException) {
            jsonObject.put("code", 500);
            jsonObject.put("msg", "系统错误");
            e.printStackTrace();
        } else {
            jsonObject.put("code", 500);
            jsonObject.put("msg", "系统错误");
            e.printStackTrace();
        }
        return jsonObject;
    }
}
