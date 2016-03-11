package com.markeloff.practise.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.markeloff.practise.R;

/**
 * Created by Markeloff on 2016/2/19.
 */
public class SoundActivity extends FragmentActivity {
    private static final String TAG = SoundActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
    }

}
