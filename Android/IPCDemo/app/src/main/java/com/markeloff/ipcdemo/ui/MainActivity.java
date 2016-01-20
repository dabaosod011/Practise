package com.markeloff.ipcdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.markeloff.ipcdemo.R;
import com.markeloff.ipcdemo.data.Book;
import com.markeloff.ipcdemo.data.Person;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final static String LOG_TAG = "MainActivity";
    private final int REQ_CODE_1 = 1;
    private final int REQ_CODE_2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initComponents();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(this, "nav_camera", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "nav_gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "nav_slideshow", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "nav_manage", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "nav_share", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "nav_send", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void initComponents() {
        Button btnStartToAdd = (Button) this.findViewById(R.id.btn_start_activity_to_add);
        btnStartToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, StartForResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("First", 20);
                bundle.putInt("Second", 30);
                intent.putExtras(bundle);
                intent.setAction("Add");
                startActivityForResult(intent, REQ_CODE_1);
            }
        });

        Button btnStartToSub = (Button) this.findViewById(R.id.btn_start_activity_to_sub);
        btnStartToSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, StartForResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("First", 20);
                bundle.putInt("Second", 30);
                intent.putExtras(bundle);
                intent.setAction("Sub");
                startActivityForResult(intent, REQ_CODE_2);
            }
        });

        Button btnSerialDemo = (Button) this.findViewById(R.id.btn_serial_demo);
        btnSerialDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person mPerson = new Person();
                mPerson.setName("Markeloff");
                mPerson.setAge(30);

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SerializableDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Person", mPerson);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button btnParcelDemo = (Button) this.findViewById(R.id.btn_parcel_demo);
        btnParcelDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book mBook = new Book();
                mBook.setBookName("C++ Primer");
                mBook.setAuthor("Stanley B. Lippman");
                mBook.setPrice(83.2);

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ParcelableDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Book", mBook);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "requestCode is: " + Integer.toString(requestCode));
        Log.d(LOG_TAG, "resultCode is: " + Integer.toString(resultCode));

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
