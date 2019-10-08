package com.example.lib_eventbus;

/**
 * @author YangZhaoxin.
 * @since 2019/9/8 19:18.
 * email yangzhaoxin@hrsoft.net.
 */

public enum ThreadMode {

    // 线程随意
    POSTING,

    // 主线程
    MAIN,

    // 子线程
    BACKGROUND
}
