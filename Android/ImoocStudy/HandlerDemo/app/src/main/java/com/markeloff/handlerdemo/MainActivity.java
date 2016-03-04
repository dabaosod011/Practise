package com.markeloff.handlerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textView;
    private ImageView imageView;
    private Button buttonStop;

    private Handler handler = new Handler();
    private int images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private int index = 0;
    private MyRunnable myrunnable = new MyRunnable();

    private Handler handler2 = new Handler(){
        public void handleMessage(Message msg){
            textView.setText("arg1 is: " + msg.arg1 + ", arg2 is: "+ msg.arg2 + ", obj is: " + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonStop = (Button) findViewById(R.id.btn_stop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(myrunnable);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: start the runnable to refresh image.");
        handler.postDelayed(myrunnable, 1000);

        new Thread(){
            public void run(){
                try {
                    Thread.sleep(2000,0);
                   // Message message = new Message();
                    Message message = handler2.obtainMessage();
                    message.arg1 = 88;
                    message.arg2 = 100;
                    Person person = new Person();
                    person.name = "Bear";
                    person.age = 30;
                    message.obj = person;
                    message.sendToTarget();
                    //handler2.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

       /* new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000, 0);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Update Thread.");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            index++;
            index = index % 3;
            Log.d(TAG, "run: image index is: "+ index);
            imageView.setImageResource(images[index]);
            handler.postDelayed(myrunnable, 1000);
        }
    }

    class Person{
        public String name;
        public int age;

        @Override
        public String toString() {
            return "Name is:" + name + ", age is: " + age;
        }
    };
}
