package com.markeloff.ipcdemo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.markeloff.ipcdemo.R;
import com.markeloff.ipcdemo.data.Person;

public class SerializableDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_demo);
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

        initComponents();
    }

    void initComponents(){
        TextView mTextView = (TextView) this.findViewById(R.id.id_text_person);
        Person mPerson = (Person)getIntent().getSerializableExtra("Person");
        mTextView.setText("name is: " + mPerson.getName() + "\n"+
                "age is: " + mPerson.getAge());
    }

}
