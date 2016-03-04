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

public class FourthActivity extends AppCompatActivity {
    private static final String TAG = FourthActivity.class.getSimpleName();

    private Button buttonSend;
    private Button buttonStop;
    private Button buttonNext;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: Main Handler.");
            Message message = new Message();
            threadHandler.sendMessageDelayed(message,1000);
        }
    };

    private Handler threadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        buttonSend = (Button) findViewById(R.id.btn_send);
        buttonStop = (Button) findViewById(R.id.btn_stop);
        buttonNext = (Button) findViewById(R.id.btn_next4);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click to send message.");;
                handler.sendEmptyMessage(1);
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click to stop message.");
                handler.removeMessages(1);
                threadHandler.removeMessages(1);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FourthActivity.this, FifthActivity.class);
                startActivity(intent);
            }
        });

        HandlerThread thread = new HandlerThread("handler thread");
        thread.start();
        threadHandler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: Thread Handler.");
                Message message = new Message();
                handler.sendMessageDelayed(message, 1000);
            }
        };

    }
}
