package com.markeloff.practise.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.markeloff.practise.R;
import com.markeloff.practise.ipc.data.Book;
import com.markeloff.practise.ipc.data.Person;

public class IpcDemoActivity extends AppCompatActivity {
    private static final String TAG = IpcDemoActivity.class.getSimpleName();

    private final int REQ_CODE_1 = 1;
    private final int REQ_CODE_2 = 2;
    private Button btn_start_add;
    private Button btn_start_sub;
    private Button btn_parcelable_demo;
    private Button btn_serialable_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_demo);
        setTitle("IPC Demo");

        initComponents();
    }

    private void initComponents(){
        btn_start_add = (Button) this.findViewById(R.id.btn_add);
        btn_start_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IpcDemoActivity.this, CalcResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("First", 20);
                bundle.putInt("Second", 30);
                intent.putExtras(bundle);
                intent.setAction("Add");
                startActivityForResult(intent, REQ_CODE_1);
            }
        });

        btn_start_sub = (Button) this.findViewById(R.id.btn_sub);
        btn_start_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IpcDemoActivity.this, CalcResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("First", 20);
                bundle.putInt("Second", 30);
                intent.putExtras(bundle);
                intent.setAction("Sub");
                startActivityForResult(intent, REQ_CODE_2);
            }
        });

        btn_serialable_demo = (Button) this.findViewById(R.id.btn_serial_demo);
        btn_serialable_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person mPerson = new Person();
                mPerson.setName("Markeloff");
                mPerson.setAge(30);

                Intent intent = new Intent();
                intent.setClass(IpcDemoActivity.this, SerializableDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Person", mPerson);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_parcelable_demo = (Button) this.findViewById(R.id.btn_parcel_demo);
        btn_parcelable_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book mBook = new Book();
                mBook.setBookName("C++ Primer");
                mBook.setAuthor("Stanley B. Lippman");
                mBook.setPrice(83.2);

                Intent intent = new Intent();
                intent.setClass(IpcDemoActivity.this, ParcelableDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Book", mBook);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "requestCode is: " + Integer.toString(requestCode));
        Log.d(TAG, "resultCode is: " + Integer.toString(resultCode));

        switch (requestCode) {
            case REQ_CODE_1:
                if (RESULT_OK == resultCode) {
                    Bundle bundle = data.getExtras();
                    int result = bundle.getInt("Result");
                    Toast.makeText(this, "onActivityResult: sum = " + result, Toast.LENGTH_SHORT).show();
                }
                break;
            case REQ_CODE_2:
                if (RESULT_OK == resultCode) {
                    Bundle bundle = data.getExtras();
                    int result = bundle.getInt("Result");
                    Toast.makeText(this, "onActivityResult: sub = " + result, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
