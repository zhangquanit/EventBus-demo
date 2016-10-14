package com.eventbus.demo.event;

public class Item implements  ItemInterface {
    public String content;

    public Item() {

    }

    public Item(String content) {
        this.content = content;
    }
}