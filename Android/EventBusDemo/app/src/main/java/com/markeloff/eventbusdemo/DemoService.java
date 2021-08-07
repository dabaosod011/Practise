package com.markeloff.eventbusdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DemoService extends Service {
    private static final String TAG = "DemoService";

    public DemoService() {
    }

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

}
