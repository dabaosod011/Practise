package com.markeloff.practise.ipc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.markeloff.practise.R;
import com.markeloff.practise.ipc.data.Person;

public class SerializableDemoActivity extends AppCompatActivity {
    private static final String TAG = SerializableDemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_demo);
        setTitle("Serializable Demo");

        initComponents();
    }

    void initComponents(){
        TextView mTextView = (TextView) this.findViewById(R.id.tv_serializable);
        Person mPerson = (Person)getIntent().getSerializableExtra("Person");
        mTextView.setText("name is: " + mPerson.getName() + "\n" +
                "age is: " + mPerson.getAge());
    }

}
