package com.markeloff.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class CalculateService extends Service {
    private static final String TAG = CalculateService.class.getSimpleName();
    private DemoBinder demoBinder = new DemoBinder();

    public CalculateService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate in.");
        Log.d(TAG, "CalculateService thread id is " + Thread.currentThread().getId());

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Title")
                .setContentText("Content")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        startForeground(1, notification);

        /*
        *   should not do complex job in service;
        *   user separate thread to do complex jobs
        */
        /* try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        super.onCreate();
        Log.d(TAG, "onCreate out.");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy in.");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand in.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Doing something in background in separate thread.");
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind in.");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind in.");
        super.onRebind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind in.");
        return demoBinder;
        //  TODO: Return the communication channel to the service.
        //  throw new UnsupportedOperationException("Not yet implemented");
    }

    class DemoBinder extends Binder {
        private final String TAG = getClass().getSimpleName();

        public void doCalc() {
            Log.d(TAG, "doCalc in.");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Doing calculate in background in separate thread.");
                }
            }).start();
        }
    }
}
