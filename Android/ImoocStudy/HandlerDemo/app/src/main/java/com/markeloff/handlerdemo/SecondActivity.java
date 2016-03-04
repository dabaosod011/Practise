package com.markeloff.handlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = SecondActivity.class.getSimpleName();

    private MyThread myThread;
    private Button buttonNext;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "In UI, currentThread: " + Thread.currentThread());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonNext = (Button) findViewById(R.id.btn_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        myThread = new MyThread();
        myThread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handler.sendEmptyMessage(2);
        myThread.handler.sendEmptyMessage(1);

        /*
         handler = new Handler(myThread.looper) {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("msg is:" + msg);
            }
        };
        handler.sendEmptyMessage(1);*/
    }

    class MyThread extends Thread {

        public Handler handler;
        public Looper looper;

        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.d(TAG, "In handler, currentThread: " + Thread.currentThread());
                }
            };
            Looper.loop();
        }
    }
}
