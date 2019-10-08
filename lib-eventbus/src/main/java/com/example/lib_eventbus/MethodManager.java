package com.example.lib_eventbus;

import java.lang.reflect.Method;

/**
 * @author YangZhaoxin.
 * @since 2019/9/7 21:46.
 * email yangzhaoxin@hrsoft.net.
 */

public class MethodManager {

    private Class<?> type;
    private Method method;
    ThreadMode threadMode;

    public MethodManager(Class<?> type, Method method, ThreadMode threadMode) {
        this.type = type;
        this.method = method;
        this.threadMode = threadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }
}
