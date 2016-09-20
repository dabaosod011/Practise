package com.haixiao.jnidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHeartRate = (TextView) findViewById(R.id.tv_heart_rate);

        NdkJniUtils ndkJniUtils = new NdkJniUtils();
        float heartRate = ndkJniUtils.calcHeartRate(SampleData.ecgSample);
        tvHeartRate.setText("heart rate = " + heartRate);
    }
}
