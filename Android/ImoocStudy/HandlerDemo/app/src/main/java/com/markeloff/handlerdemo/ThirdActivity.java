package com.markeloff.handlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = ThirdActivity.class.getSimpleName();

    private HandlerThread handlerThread;
    private Handler handler;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        button = (Button) findViewById(R.id.btn_next2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ThirdActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        handlerThread = new HandlerThread("Handler Thread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: In handler, currentThread is:"  + Thread.currentThread());
            }
        };
        handler.sendEmptyMessage(1);
    }
}
