package com.eventbus.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eventbus.demo.event.Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author 张全
 */
public class TwoAct extends Activity implements View.OnClickListener{
    private static final String TAG = "EventBus";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.result).setVisibility(View.GONE);
        findViewById(R.id.btn_mainThread).setOnClickListener(this);
        findViewById(R.id.btn_postThread).setOnClickListener(this);
        findViewById(R.id.btn_backgroundThread).setOnClickListener(this);
        findViewById(R.id.btn_Async).setOnClickListener(this);

        EventBus.getDefault().register(this);
    }

    //-----------------------注册事件 START-------------------
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onEventMainThread(Item item) {
        Log.d(TAG, "TwoAct onEventMainThread: "+item.content);
    }
    @Subscribe(threadMode=ThreadMode.POSTING)
    public void onEventPostThread(Item item) {
        Log.d(TAG, "TwoAct onEventPostThread: "+item.content);
    }
    @Subscribe(threadMode=ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(Item item) {
        Log.d(TAG, "TwoAct onEventBackgroundThread: "+item.content);
    }
    @Subscribe(threadMode=ThreadMode.ASYNC)
    public void onEventAsync(Item item) {
        Log.d(TAG, "TwoAct onEventAsync: "+item.content);
    }
    //-----------------------注册事件 END-------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mainThread:
                EventBus.getDefault().post(new Item("from TwoAct MainThread"));
                break;
            case R.id.btn_postThread:
                EventBus.getDefault().post(new Item("from TwoAct PostThread"));
                break;
            case R.id.btn_backgroundThread:
                EventBus.getDefault().post(new Item("from TwoAct BackgroundThread"));
                break;
            case R.id.btn_Async:
                EventBus.getDefault().post(new Item("from TwoAct Async"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
