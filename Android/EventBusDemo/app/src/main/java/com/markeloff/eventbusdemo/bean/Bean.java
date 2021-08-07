package com.markeloff.eventbusdemo.bean;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Hai Xiao on 21/10/2017.
 *
 * wenxin technology inc.
 *
 * hai.xiao@wen-xintech.com
 *
 * Copyright (c) 2017 wenxin technology inc. All rights reserved.
 */

@Table(name = "eventbus_demo") public class Bean extends SugarRecord {

    @SerializedName("content") public String content;

    @SerializedName("flag") public boolean flag;

    @Override public String toString() {
        return new GsonBuilder().create().toJson(this, Bean.class);
    }
}
