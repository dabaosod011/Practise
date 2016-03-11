package com.markeloff.practise.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.markeloff.practise.R;

public class FragmentDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);
        setTitle("Fragment Demo");
    }
}
