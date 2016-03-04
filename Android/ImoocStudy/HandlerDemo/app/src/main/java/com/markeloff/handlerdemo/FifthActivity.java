package com.markeloff.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class FifthActivity extends AppCompatActivity {
    private static final String TAG = FifthActivity.class.getSimpleName();
    private TextView textView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: refresh the UI. " );
            textView.setText("OK2");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        textView = (TextView) findViewById(R.id.textView5);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    notifyUIFresh1();

                    Thread.sleep(2000);
                    notifyUIFresh2();

                    Thread.sleep(2000);
                    notifyUIFresh3();

                    Thread.sleep(2000);
                    notifyUIFresh4();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void notifyUIFresh1(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("OK1");
            }
        });
    }

    private void notifyUIFresh2(){
        handler.sendEmptyMessage(1);
    }

    private void notifyUIFresh3(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("OK3");
            }
        });
    }

    private void notifyUIFresh4(){
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("OK4");
            }
        });
    }
}
