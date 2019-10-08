package com.example.eventbus;

import android.app.Application;

import com.example.lib_network.NetworkManager;

/**
 * @author YangZhaoxin.
 * @since 2019/9/28 20:01.
 * email yangzhaoxin@hrsoft.net.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance().init(this);
    }
}
