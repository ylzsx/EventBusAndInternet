package com.example.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lib_eventbus.EventBus;

/**
 * @author YangZhaoxin.
 * @since 2019/9/7 22:01.
 * email yangzhaoxin@hrsoft.net.
 */

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }

    public void sendMessage(View view) {

        EventBus.getInstance().post(new String("aaa"));
    }
}
