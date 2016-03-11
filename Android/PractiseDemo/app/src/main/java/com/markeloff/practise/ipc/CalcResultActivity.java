package com.markeloff.practise.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.markeloff.practise.R;

public class CalcResultActivity extends AppCompatActivity {
    private static final String TAG = CalcResultActivity.class.getSimpleName();
    private int first;
    private int second;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_result);
        setTitle("startActivityForResult Demo");

        TextView tv_result = (TextView) findViewById(R.id.tv_calc_result);
        Intent intent = getIntent();
        String action = intent.getAction();

        Log.d(TAG, "onCreate: action is " + action);
        if (action.equalsIgnoreCase("add")) {
            Bundle bundle = intent.getExtras();
            first = bundle.getInt("First");
            second = bundle.getInt("Second");

            result = first + second;
            tv_result.setText(""+first+" + " + second + " = " + result );
        } else if (action.equalsIgnoreCase("sub")) {
            Bundle bundle = intent.getExtras();
            first = bundle.getInt("First");
            second = bundle.getInt("Second");

            result = first - second;
            tv_result.setText(""+first+" - " + second + " = " + result );
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Result", result);

        CalcResultActivity.this.setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}
