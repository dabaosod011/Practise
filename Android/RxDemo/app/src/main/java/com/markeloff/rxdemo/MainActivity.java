package com.markeloff.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @Bind(R.id.btn_1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RxDemo mDemo = new RxDemo();
        mDemo.rxDemoForFun1();
        mDemo.rxDemoForFun2();
        mDemo.rxDemoForFun3();
    }

    @OnClick(R.id.btn_1)
    public void onClick() {
        Log.d(TAG, "onClick: btn_1");
    }
}
