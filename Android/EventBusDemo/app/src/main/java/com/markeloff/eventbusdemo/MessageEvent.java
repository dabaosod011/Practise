package com.markeloff.eventbusdemo;

/**
 * Created by Hai Xiao on 21/10/2017.
 *
 * wenxin technology inc.
 *
 * hai.xiao@wen-xintech.com
 *
 * Copyright (c) 2017 wenxin technology inc. All rights reserved.
 */

public class MessageEvent {

    public final String name;
    public final int count;

    public MessageEvent(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
