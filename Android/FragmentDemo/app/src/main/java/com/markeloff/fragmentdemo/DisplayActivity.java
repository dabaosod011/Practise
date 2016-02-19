package com.markeloff.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Hai Xiao on 2016/2/19.
 * hai.xiao@wen-xintech.com
 */
public class DisplayActivity extends FragmentActivity {
    private static final String TAG = DisplayActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
    }

}