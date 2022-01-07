package com.itheima.reggie.demo.common;

public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCreatId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCreatId() {
        return threadLocal.get();
    }
}
