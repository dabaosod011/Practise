package com.markeloff.practise.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markeloff.practise.R;

/**
 * Created by Markeloff on 2016/2/19.
 */

public class SoundFragment extends Fragment {
    private static final String TAG = SoundFragment.class.getSimpleName();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_sound, container, false);
        return view;
    }
}

