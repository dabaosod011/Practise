package com.markeloff.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxDemo mDemo = new RxDemo();
        mDemo.rxDemoForFun1();
        mDemo.rxDemoForFun2();
    }


}
