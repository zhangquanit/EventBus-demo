package com.eventbus.demo;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * 张全
 */

public class BaseActivity extends Activity {
    private static final String TAG = "EventBus";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        //注册监听事件对象
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //-----------------------注册事件 START-------------------
//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 0)
//    public void onItemEvent(Item item) {
//        Log.d(TAG, "######MainActivity onEventMainThread: ================start");
//        for (int i = 0; i < 20000; i++) {
//            System.out.print(String.valueOf(i));
//        }
//        Log.d(TAG, "######MainActivity onEventMainThread: " + item.content + "  curThread=" + Thread.currentThread().getName());
//        Log.d(TAG, "######MainActivity onEventMainThread: ================end");
//    }
//
//    @Subscribe(threadMode = ThreadMode.POSTING)
//    public void onEventPostThread(Item item) {
//        Log.d(TAG, "MainActivity onEventPostThread: " + item.content + "  curThread=" + Thread.currentThread().getName());
//    }
//
//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onEventBackgroundThread(Item item) {
//        Log.d(TAG, "MainActivity onEventBackgroundThread: " + item.content + "  curThread=" + Thread.currentThread().getName());
//    }
//
//    @Subscribe(threadMode = ThreadMode.ASYNC)
//    public void onEventAsync(Item item) {
//        Log.d(TAG, "MainActivity onEventAsync: " + item.content + "  curThread=" + Thread.currentThread().getName());
//    }

}
