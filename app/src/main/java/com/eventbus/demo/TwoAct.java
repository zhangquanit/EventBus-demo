package com.eventbus.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eventbus.demo.event.EventAction;
import com.eventbus.demo.event.Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.event.PostEvent;

/**
 * @author 张全
 */
public class TwoAct extends Activity implements View.OnClickListener {
    private static final String TAG = "EventBus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        findViewById(R.id.btn_mainThread).setOnClickListener(this);
        findViewById(R.id.btn_postThread).setOnClickListener(this);
        findViewById(R.id.btn_eventInheritance).setOnClickListener(this);
        findViewById(R.id.btn_action).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mainThread://主线程中发送事件
                EventBus.getDefault().post(new Item("[from TwoAct  MainThread]"));
                break;
            case R.id.btn_postThread: //子线程中发送事件
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "post:  curThread=" + Thread.currentThread().getName());
                        EventBus.getDefault().post(new Item("[from TwoAct  PostThread]"));
                    }
                }).start();
                break;
            case R.id.btn_eventInheritance: //事件继承
                EventBus.getDefault().post(new Item("[from TwoAct，eventInheritance->Item]"));
//                EventBus.getDefault().post(new ItemTwo("[from TwoAct，eventInheritance->ItemTwo]"));
                break;
            case R.id.btn_action:  //事件动作
                EventBus.getDefault().post(new PostEvent(EventAction.ACTION));
//                EventBus.getDefault().post(new PostEvent(EventAction.ACTION2));
                break;
        }
    }

}
