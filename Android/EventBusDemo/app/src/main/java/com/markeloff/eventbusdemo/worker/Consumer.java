package com.markeloff.eventbusdemo.worker;

import android.util.Log;
import com.markeloff.eventbusdemo.bean.Bean;
import com.orm.query.Condition;
import com.orm.query.Select;
import java.util.List;

/**
 * Created by Hai Xiao on 21/10/2017.
 *
 * wenxin technology inc.
 *
 * hai.xiao@wen-xintech.com
 *
 * Copyright (c) 2017 wenxin technology inc. All rights reserved.
 */

public class Consumer implements Runnable {
    private static final String TAG = "Consumer";

    private int quota;

    public Consumer(int quota_) {
        this.quota = quota_;
    }

    @Override public void run() {
        List<Bean> beanList = Select.from(Bean.class)
              .where(Condition.prop("flag").eq(0))
              .limit(String.valueOf(quota))
              .list();

        if (null == beanList || beanList.size() == 0) {
            Log.d(TAG, "run: nothing to consume.");
        } else {
            Log.d(TAG, "run: consume " + beanList.size() + " beans.");

            for (Bean b : beanList) {
                b.flag = true;
                b.save();
            }
        }
    }
}
