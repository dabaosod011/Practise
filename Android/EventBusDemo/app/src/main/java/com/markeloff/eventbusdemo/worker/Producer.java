package com.markeloff.eventbusdemo.worker;

import android.util.Log;
import com.markeloff.eventbusdemo.bean.Bean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hai Xiao on 21/10/2017.
 *
 * wenxin technology inc.
 *
 * hai.xiao@wen-xintech.com
 *
 * Copyright (c) 2017 wenxin technology inc. All rights reserved.
 */

public class Producer implements Runnable {
    private static final String TAG = "Producer";

    private int count;

    public Producer(int count_) {
        this.count = count_;
    }

    @Override public void run() {
        Log.d(TAG, "run: produce " + count + " beans.");

        for (int i = 0; i < count; i++) {
            Bean b = new Bean();

            b.content = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(
                  new Date().getTime());
            b.flag = false;
            b.save();
        }
    }
}
