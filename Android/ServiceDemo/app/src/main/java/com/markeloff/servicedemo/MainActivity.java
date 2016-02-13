package com.markeloff.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.btn_start_service)
    Button btnStartService;
    @Bind(R.id.btn_stop_service)
    Button btnStopService;
    @Bind(R.id.btn_bind_service)
    Button btnBindService;
    @Bind(R.id.btn_unbind_service)
    Button btnUnbindService;

    private CalculateService.DemoBinder mBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (CalculateService.DemoBinder) service;
            mBinder.doCalc();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "MainActivity thread id is " + Thread.currentThread().getId());
    }

    @OnClick({R.id.btn_start_service, R.id.btn_stop_service, R.id.btn_bind_service, R.id.btn_unbind_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service:
                Log.d(TAG, "click on start service");
                Intent startIntent = new Intent(this, CalculateService.class);
                startService(startIntent);
                break;
            case R.id.btn_stop_service:
                Log.d(TAG, "click on stop service");
                Intent stopIntent = new Intent(this, CalculateService.class);
                stopService(stopIntent);
                break;
            case R.id.btn_bind_service:
                Log.d(TAG, "click bind service");
                Intent bindIntent = new Intent(this, CalculateService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                Log.d(TAG, "click unbind service");
                unbindService(connection);
                break;
            default:
                break;
        }
    }

}
