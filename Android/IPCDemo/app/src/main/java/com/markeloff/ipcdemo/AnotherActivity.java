package com.markeloff.ipcdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AnotherActivity extends AppCompatActivity {
    private int first;
    private int second;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        String action = intent.getAction();
        if (action.equalsIgnoreCase("add")) {
            Bundle bundle = intent.getExtras();
            first = bundle.getInt("First");
            second = bundle.getInt("Second");

            result = first + second;
        } else if (action.equalsIgnoreCase("sub")) {
            Bundle bundle = intent.getExtras();
            first = bundle.getInt("First");
            second = bundle.getInt("Second");

            result = first - second;

        }
        initComponents();
    }

    void initComponents() {
        Button btnClose = (Button) this.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Result", result);

                AnotherActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
