package com.example.lib_eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author YangZhaoxin.
 * @since 2019/9/7 21:39.
 * email yangzhaoxin@hrsoft.net.
 */

public class EventBus {

    private Map<Object, List<MethodManager>> map;
    private Handler handler;
    private ExecutorService executorService;

    private EventBus() {
        map = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    private static class InstanceHolder {
        private static EventBus instance = new EventBus();
    }

    public static EventBus getInstance() {
        return InstanceHolder.instance;
    }

    public void register(Object object) {
        List<MethodManager> methodManagers = map.get(object);
        if (methodManagers == null) {
            methodManagers = findMethod(object);
            map.put(object, methodManagers);
        }
    }

    /**
     * 找订阅方法集
     * @param object
     * @return
     */
    private List<MethodManager> findMethod(Object object) {
        List<MethodManager> methodManagers = new ArrayList<>();
        Class<?> aClass = object.getClass();

        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            Subscribe annotation = declaredMethod.getAnnotation(Subscribe.class);
            if (annotation == null) {
                continue;
            }
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            if (parameterTypes.length != 1) {
                continue;
            }
            ThreadMode threadMode = annotation.threadMode();
            MethodManager methodManager = new MethodManager(parameterTypes[0], declaredMethod, threadMode);
            methodManagers.add(methodManager);
        }
        return methodManagers;
    }


    /**
     * 发布
     * @param setter
     */
    public void post(final Object setter) {
        Iterator<Object> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            List<MethodManager> methodManagers = map.get(next);

            if (methodManagers != null) {
                for (final MethodManager methodManager : methodManagers) {
                    if (methodManager.getType().isAssignableFrom(setter.getClass())) {
                        switch (methodManager.getThreadMode()) {
                            case POSTING:
                                invoke(next, methodManager.getMethod(), setter);
                                break;
                            case MAIN:
                                if (Looper.myLooper() == Looper.getMainLooper()) {  // 判断当前线程是否为主线程
                                    invoke(next, methodManager.getMethod(), setter);
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            invoke(next, methodManager.getMethod(), setter);
                                        }
                                    });
                                }
                                break;
                            case BACKGROUND:
                                if (Looper.myLooper() == Looper.getMainLooper()) {
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            invoke(next, methodManager.getMethod(), setter);
                                        }
                                    });
                                } else {
                                    invoke(next, methodManager.getMethod(), setter);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(Object object, Method method, Object setter) {
        try {
            method.invoke(object, setter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object object) {
        if (map.get(object) != null) {
            map.remove(object);
        }
    }
}
