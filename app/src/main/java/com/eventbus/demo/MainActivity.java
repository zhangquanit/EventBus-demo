package com.eventbus.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eventbus.demo.event.Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.event.PostEvent;

/**
 * ThreadMode.MAIN代表这个方法会在UI线程执行
 * ThreadMode.POSTING代表这个方法会在当前发布事件的线程执行
 * ThreadMode.BACKGROUND 如果事件是在UI线程中发布出来的，那么就会在子线程中运行，如果事件本来就是子线程中发布出来的，那么直接在该子线程中执行。
 * ThreadMode.ASYNC 事件执行在一个独立的异步线程中。强制在后台执行
 * <p>
 * 如果由于事件的发布者是在子线程中，所以BACKGROUND与POSTING模式下订阅者与事件的发布者运行在同一个线程。
 * 而ASYNC模式下又重新开起一个线程来执行任务。Main模式则是在主线程中运行
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "EventBus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_mainThread).setOnClickListener(this);
        findViewById(R.id.btn_postThread).setOnClickListener(this);
        findViewById(R.id.btn_backgroundThread).setOnClickListener(this);
        findViewById(R.id.btn_Async).setOnClickListener(this);
        findViewById(R.id.btn_Act).setOnClickListener(this);

        //测试Service里面的监听
        startService(new Intent(this,MyService.class));

    }

    //-----------------------注册事件 START-------------------
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 0)
    public void onItemEvent(Item item) {
        Log.d(TAG, "######MainActivity onEventMainThread: ================start");
        for (int i = 0; i < 20000; i++) {
            System.out.print(String.valueOf(i));
        }
        Log.d(TAG, "######MainActivity onEventMainThread: " + item.content + "  curThread=" + Thread.currentThread().getName());
        Log.d(TAG, "######MainActivity onEventMainThread: ================end");
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventPostThread(Item item) {
        Log.d(TAG, "MainActivity onEventPostThread: " + item.content + "  curThread=" + Thread.currentThread().getName());
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(Item item) {
        Log.d(TAG, "MainActivity onEventBackgroundThread: " + item.content + "  curThread=" + Thread.currentThread().getName());
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(Item item) {
        Log.d(TAG, "MainActivity onEventAsync: " + item.content + "  curThread=" + Thread.currentThread().getName());
    }
    private static final String ACTION="com.eventbus.demo.action";
    private static final String ACTION2="com.eventbus.demo.action2";
    @Subscribe(threadMode = ThreadMode.ASYNC,actions = {ACTION,ACTION2})
    public void onEventAction(PostEvent item) {
        Log.d(TAG, "MainActivity onEventAction: [" + item.getAction() + "]  curThread=" + Thread.currentThread().getName());
    }
    @Subscribe(threadMode = ThreadMode.ASYNC,actions = {ACTION2})
    public void onEventAction2(PostEvent item) {
        Log.d(TAG, "MainActivity onEventAction2: [" + item.getAction() + "]  curThread=" + Thread.currentThread().getName());
    }

    //-----------------------注册事件 END-------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mainThread:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "post:  curThread=" + Thread.currentThread().getName());
                        EventBus.getDefault().postSticky(new Item("from MainActivity  MainThread"));
                        for (int i = 0; i < 10000; i++) {
                            System.out.print(String.valueOf(i));
                        }
                        Log.d(TAG, "post ================end");
                    }
                }).start();

                break;
            case R.id.btn_postThread:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "post:  curThread=" + Thread.currentThread().getName());
                        EventBus.getDefault().post(new Item("from MainActivity  PostThread"));
                    }
                }).start();
                break;
            case R.id.btn_backgroundThread:
                EventBus.getDefault().post(new PostEvent(ACTION));
                break;
            case R.id.btn_Async:
                EventBus.getDefault().post(new PostEvent(ACTION2));
                break;
            case R.id.btn_Act:
                startActivity(new Intent(this, TwoAct.class));
                break;
        }
    }

}
