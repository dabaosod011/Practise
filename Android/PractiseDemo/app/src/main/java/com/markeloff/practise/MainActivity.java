package com.markeloff.practise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.markeloff.practise.ActionBarShare.SendTextActivity;
import com.markeloff.practise.Retrofit.RetrofitDemoActivity;
import com.markeloff.practise.bluetooth.classic.BlueToothClassicActivity;
import com.markeloff.practise.fragment.FragmentDemoActivity;
import com.markeloff.practise.ipc.IpcDemoActivity;
import com.markeloff.practise.rx.RxDemoActivity;
import com.markeloff.practise.service.ServiceDemoActivity;
import com.markeloff.practise.storage.StorageDemoActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        ((Button) findViewById(R.id.btn_storage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, StorageDemoActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_fragment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, FragmentDemoActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_ipc)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, IpcDemoActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, ServiceDemoActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_rx)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, RxDemoActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_retrofit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, RetrofitDemoActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_send_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, SendTextActivity.class);
            }
        });

        ((Button) findViewById(R.id.btn_bluetooth)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.this, BlueToothClassicActivity.class);
            }
        });
    }

    private void startNewActivity(Context context, Class<?> cls){
        Log.d(TAG, "startNewActivity: " + cls.getSimpleName());
        Intent intent = new Intent();
        intent.setClass(context, cls);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
