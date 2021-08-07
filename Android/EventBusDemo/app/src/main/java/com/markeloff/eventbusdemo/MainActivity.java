package com.markeloff.eventbusdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.markeloff.eventbusdemo.worker.Consumer;
import com.markeloff.eventbusdemo.worker.Producer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private boolean stopFlag = false;
    private Button btnStop;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                stopFlag = true;
            }
        });

        Observable.interval(5, TimeUnit.SECONDS)
              .observeOn(AndroidSchedulers.mainThread())
              .takeUntil(new Predicate<Long>() {
                  @Override public boolean test(@NonNull Long aLong) throws Exception {
                      return stopFlag;
                  }
              })
              .subscribe(i -> produce(i));
    }

    private void produce(long count) {
        Log.d(TAG, "produce: count = " + count);

        new Producer((int) count).run();
        EventBus.getDefault().post(new MessageEvent("event", (int) count));
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void handleEventBus(MessageEvent messageEvent) {
        Log.d(TAG, "handleEventBus: messageEvent = " + messageEvent.toString());
        new Consumer(messageEvent.count ).run();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
