package com.eventbus.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eventbus.demo.event.Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * ThreadMode.MAIN代表这个方法会在UI线程执行
 * ThreadMode.POSTING代表这个方法会在当前发布事件的线程执行
 * ThreadMode.BACKGROUND这个方法，如果在非UI线程发布的事件，则直接执行，和发布在同一个线程中。如果在UI线程发布的事件，则加入后台任务队列，使用线程池一个接一个调用。
 * ThreadMode.ASYNC 加入后台任务队列，使用线程池调用，注意没有BackgroundThread中的一个接一个。
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EventBus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_mainThread).setOnClickListener(this);
        findViewById(R.id.btn_postThread).setOnClickListener(this);
        findViewById(R.id.btn_backgroundThread).setOnClickListener(this);
        findViewById(R.id.btn_Async).setOnClickListener(this);
        findViewById(R.id.btn_twoAct).setOnClickListener(this);

        //注册监听事件对象
        EventBus.getDefault().register(this);
    }

    //-----------------------注册事件 START-------------------
    @Subscribe(threadMode=ThreadMode.MAIN)
    public void onItemEvent(Item item) {
        Log.d(TAG, "MainActivity onEventMainThread: "+item.content);
    }
    @Subscribe(threadMode=ThreadMode.POSTING)
    public void onEventPostThread(Item item) {
        Log.d(TAG, "MainActivity onEventPostThread: "+item.content);
    }
    @Subscribe(threadMode=ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(Item item) {
        Log.d(TAG, "MainActivity onEventBackgroundThread: "+item.content);
    }
    @Subscribe(threadMode=ThreadMode.ASYNC)
    public void onEventAsync(Item item) {
        Log.d(TAG, "MainActivity onEventAsync: "+item.content);
    }

    //-----------------------注册事件 END-------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mainThread:
                EventBus.getDefault().post(new Item("from MainActivity  MainThread"));
                break;
            case R.id.btn_postThread:
                EventBus.getDefault().post(new Item("from MainActivity  PostThread"));
                break;
            case R.id.btn_backgroundThread:
                EventBus.getDefault().post(new Item("from MainActivity  BackgroundThread"));
                break;
            case R.id.btn_Async:
                EventBus.getDefault().post(new Item("from MainActivity  Async"));
                break;
            case R.id.btn_twoAct:
                startActivity(new Intent(this,TwoAct.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销事件
        EventBus.getDefault().unregister(this);
    }

}
