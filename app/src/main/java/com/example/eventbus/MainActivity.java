package com.example.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lib_eventbus.EventBus;
import com.example.lib_eventbus.Subscribe;
import com.example.lib_eventbus.ThreadMode;
import com.example.lib_network.NetworkManager;
import com.example.lib_network.annotation.Network;
import com.example.lib_network.type.NetType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnJump = (Button) findViewById(R.id.btn_jump);
        mBtnJump.setOnClickListener(this);

        EventBus.getInstance().register(this);

//        NetworkManager.getInstance().setListener(this);
        NetworkManager.getInstance().register(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getString1(String data) {
        Log.e("EventBus", "One1 " + Thread.currentThread().getName() + " " + data);
    }

    @Subscribe
    public void getString2(String data) {
        Log.e("EventBus", "One2 " + Thread.currentThread().getName() + " " + data);
    }

    @Subscribe
    public void getInt(int data) {
        Log.e("MainActivity", "int" + data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unregister(this);
        NetworkManager.getInstance().unRegister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(this, ThreeActivity.class);
                startActivity(intent);
                break;
        }
    }

//    @Override
//    public void onConnect(NetType netType) {
//
//    }
//
//    @Override
//    public void onDisConnect() {
//
//    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType) {
        switch (netType) {
            case NONE:
                Log.e(this.getClass().getSimpleName(), "没有网络");
                break;
            case CMNET:
            case CMWAP:
                Log.e(this.getClass().getSimpleName(), netType.name());
                break;
            case AUTO:
                Log.e(this.getClass().getSimpleName(), "连接到网络");
                break;
        }
    }

}
