package com.example.lib_network.listener;

import com.example.lib_network.type.NetType;

/**
 * @author YangZhaoxin.
 * @since 2019/9/28 20:23.
 * email yangzhaoxin@hrsoft.net.
 */

public interface NetChangeObserver {

    void onConnect(NetType netType);

    void onDisConnect();
}
