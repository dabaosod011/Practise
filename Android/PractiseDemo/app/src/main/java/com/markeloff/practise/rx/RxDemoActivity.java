package com.markeloff.practise.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.markeloff.practise.R;

public class RxDemoActivity extends AppCompatActivity {
    private static final String TAG = RxDemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_demo);
        setTitle("Rx Demo");

        Log.d(TAG, "onCreate: Rx Demo begins.");
        RxDemo mDemo = new RxDemo();
        mDemo.rxDemoForFun1();
        mDemo.rxDemoForFun2();
        mDemo.rxDemoForFun3();
    }
}
