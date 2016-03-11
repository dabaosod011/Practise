package com.markeloff.practise.storage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.markeloff.practise.R;

public class StorageDemoActivity extends AppCompatActivity {
    private static final String TAG = StorageDemoActivity.class.getSimpleName();

    private TextView tv_storage_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_demo);
        setTitle("Storage Demo");

        tv_storage_result= (TextView) findViewById(R.id.tv_storage_result);
        tv_storage_result.setText("Storage Demo Result:");

        tv_storage_result.append("\n\nEnvironment getRootDirectory(): " + Environment.getRootDirectory().toString());
        tv_storage_result.append("\nEnvironment getDataDirectory(): " + Environment.getDataDirectory().toString());
        tv_storage_result.append("\nEnvironment getDownloadCacheDirectory(): " + Environment.getDownloadCacheDirectory().toString());

        tv_storage_result.append("\n\nContext getFilesDir(): " + this.getFilesDir().toString());
        tv_storage_result.append("\nContext getPackageResourcePath(): " + this.getPackageResourcePath().toString());
        tv_storage_result.append("\nContext getCacheDir(): " + this.getCacheDir().toString());
        tv_storage_result.append("\nContext getExternalCacheDir(): " + this.getExternalCacheDir().toString());
        tv_storage_result.append("\nContext getExternalFilesDir(Environment.DIRECTORY_DCIM): " + this.getExternalFilesDir(Environment.DIRECTORY_DCIM).toString());
        tv_storage_result.append("\nContext getObbDir(): " + this.getObbDir().toString());
    }
}
