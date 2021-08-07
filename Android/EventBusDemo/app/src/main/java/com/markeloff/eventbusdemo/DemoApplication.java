package com.markeloff.eventbusdemo;

import android.app.Application;
import android.util.Log;
import com.markeloff.eventbusdemo.bean.Bean;
import com.orm.SugarContext;

/**
 * Created by Hai Xiao on 21/10/2017.
 *
 * wenxin technology inc.
 *
 * hai.xiao@wen-xintech.com
 *
 * Copyright (c) 2017 wenxin technology inc. All rights reserved.
 */

public class DemoApplication extends Application {
    private static final String TAG = "DemoApplication";

    @Override public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        SugarContext.init(this);

        Bean.deleteAll(Bean.class);
    }

    @Override public void onTerminate() {
        Log.d(TAG, "onTerminate() called");
        super.onTerminate();
        SugarContext.terminate();
    }
}
