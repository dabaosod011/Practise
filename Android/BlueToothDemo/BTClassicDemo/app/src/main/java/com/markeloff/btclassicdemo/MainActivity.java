package com.markeloff.btclassicdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    BluetoothAdapter mBluetoothAdapter = null;
    Set<BluetoothDevice> mPairedDevices = null;
    private static final int REQUEST_ENABLE_BT = 1;
    private ListView mListView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();

    @InjectView(R.id.btn_setup_bt)
    Button btnSetupBt;
    @InjectView(R.id.btn_list_bt)
    Button btnListBt;

    private final BroadcastReceiver mBTChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: Bluetooth off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "onReceive: Turning Bluetooth off...");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: Bluetooth on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "onReceive: Turning Bluetooth on...");
                        break;
                }
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBTDeviceFoundReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
            }
            mListView.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, mDeviceList));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBTChangeReceiver, filter);

        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBTDeviceFoundReceiver, filter2);

        mListView = (ListView) findViewById(R.id.listView_btdevices);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "onActivityResult: Successfully enable the BlueTooth.");
                    mBluetoothAdapter.startDiscovery();
                } else if (resultCode == RESULT_CANCELED) {
                    Log.d(TAG, "onActivityResult: Cancel to enable the BlueTooth.");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBTChangeReceiver);
        unregisterReceiver(mBTDeviceFoundReceiver);
    }

    @OnClick({R.id.btn_setup_bt, R.id.btn_list_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_setup_bt: {
                Log.d(TAG, "onClick: R.id.btn_setup_bt");
                setUpBlueTooth();
                break;
            }
            case R.id.btn_list_bt: {
                Log.d(TAG, "onClick: R.id.btn_list_bt");
                if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                    scanBlueToothDevices();
                }
                break;
            }
            default:
                break;
        }
    }

    private void setUpBlueTooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "setUpBlueTooth: Device does not support Bluetooth.");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "setUpBlueTooth: BlueTooth is disabled, promote to enable it.");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void scanBlueToothDevices() {
        Log.d(TAG, "scanBlueToothDevices.");
        mPairedDevices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "scanBlueToothDevices: mPairedDevices.size() = " + mPairedDevices.size());
        for (BluetoothDevice device : mPairedDevices) {
            Log.d(TAG, "scanBlueToothDevices: device name is: " + device.getName());
            mDeviceList.add(device.getName() + "\n" + device.getAddress());
        }
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDeviceList));

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

}
