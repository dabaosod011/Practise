package com.markeloff.practise.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.markeloff.practise.R;

public class ServiceDemoActivity extends AppCompatActivity {
    private static final String TAG = ServiceDemoActivity.class.getSimpleName();

    private DemoService.DemoBinder mBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (DemoService.DemoBinder) service;
            mBinder.doCalc();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        setTitle("Service Demo");

        initComponents();
    }

    private void initComponents() {
        ((Button) findViewById(R.id.btn_start_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click on start service");
                Intent startIntent = new Intent(ServiceDemoActivity.this, DemoService.class);
                startService(startIntent);
            }
        });
        ((Button) findViewById(R.id.btn_stop_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click on stop service");
                Intent stopIntent = new Intent(ServiceDemoActivity.this, DemoService.class);
                stopService(stopIntent);
            }
        });
        ((Button) findViewById(R.id.btn_bind_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click bind service");
                Intent bindIntent = new Intent(ServiceDemoActivity.this, DemoService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
            }
        });
        ((Button) findViewById(R.id.btn_unbind_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click unbind service");
                unbindService(connection);
            }
        });
    }
}
