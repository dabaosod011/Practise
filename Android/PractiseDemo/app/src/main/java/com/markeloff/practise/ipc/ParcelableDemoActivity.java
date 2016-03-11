package com.markeloff.practise.ipc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.markeloff.practise.R;
import com.markeloff.practise.ipc.data.Book;

public class ParcelableDemoActivity extends AppCompatActivity {
    private static final String TAG = ParcelableDemoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable_demo);
        setTitle("Parcelable Demo");

        initComponents();
    }

    void initComponents(){
        TextView mTextView = (TextView) this.findViewById(R.id.tv_parcelable);
        Book mBook = (Book)getIntent().getParcelableExtra("Book");
        mTextView.setText("Book Name is: " + mBook.getBookName() + "\n" +
                "Authur is: " + mBook.getAuthor() + "\n" +
                "Price is: " + mBook.getPrice());
    }
}
