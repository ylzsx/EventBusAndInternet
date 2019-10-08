package com.example.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lib_eventbus.EventBus;
import com.example.lib_eventbus.Subscribe;

/**
 * @author YangZhaoxin.
 * @since 2019/9/26 21:34.
 * email yangzhaoxin@hrsoft.net.
 */

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnJump = (Button) findViewById(R.id.btn_jump);
        mBtnJump.setOnClickListener(this);

        EventBus.getInstance().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(this, TwoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void getString(String data) {
        Log.e("EventBus", "Three " + Thread.currentThread().getName() + " " + data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
    }
}
