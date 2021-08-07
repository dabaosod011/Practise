package com.markeloff.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demo0();
    }

    private void demo0() {
        Observable<String> strObservable = Observable.just("A", "BB", "CCC", "DDDD", "EEEEE");

        strObservable.subscribe(s -> Log.d(TAG, "demo0: " + s));
        strObservable.map(s -> s.length()).subscribe(s -> Log.d(TAG, "demo0: " + s));
    }
}
