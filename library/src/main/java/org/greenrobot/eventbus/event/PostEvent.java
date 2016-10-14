package org.greenrobot.eventbus.event;

/**
 * 张全
 */

public class PostEvent {
    private String action;

    public PostEvent(){

    }
    public PostEvent(String action){
        this.action=action;
    }
    public void addAction(String action) {
        this.action=action;
    }

    public String getAction() {
        return action;
    }
}
