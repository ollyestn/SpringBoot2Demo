package com.xyai.shenniuban.utils;


import java.util.HashMap;

/**
 * @author lcz
 * @date 2022/05/15 11:07
 * 统一结果集
 */
public class R extends HashMap<String, Object> {

    public static final int SUCCESS_CODE = 200;

    private R() {

    }

    public static R build(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R success() {
        R r = new R();
        r.put("code", 200);
        r.put("msg", "success");
        return r;
    }

    public static R success(String key, Object value) {
        R r = R.success();
        r.put(key, value);
        return r;
    }

    public static R failure() {
        R r = new R();
        r.put("code", 500);
        r.put("msg", "操作失败");
        return r;
    }

    public static R failure(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public R add(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R delete(String key) {
        if (key != null && (!"code".equals(key) || !"msg".equals(key))) {
            this.remove(key);
        }
        return this;
    }

    public int getCode() {
        return (int) this.get("code");
    }

    public void setCode(int code) {
        this.put("code", code);
    }

    public String getMsg() {
        return (String) this.get("msg");
    }

    public void setMsg(String msg) {
        this.put("msg", msg);
    }
}
