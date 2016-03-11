package com.markeloff.practise.Retrofit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.markeloff.practise.R;
import com.markeloff.practise.Retrofit.data.Curator;
import com.markeloff.practise.Retrofit.data.IApiMethods;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDemoActivity extends AppCompatActivity {
    private static final String TAG = RetrofitDemoActivity.class.getSimpleName();

    public static final String API_URL = "https://freemusicarchive.org/api/";
    public static final String API_KEY = "75KALSW7FEA75IGH";
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);
        setTitle("Retrofix Demo");

        tv_content = (TextView) findViewById(R.id.tv_retrofit_content);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        BackgroundTask task = new BackgroundTask();
        task.execute();
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Curator> {
        Retrofit retrofit;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "BackgroundTask onPreExecute: init the retrofit.");
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        @Override
        protected Curator doInBackground(Void... params) {
            IApiMethods methods = retrofit.create(IApiMethods.class);
            Call<Curator> call = methods.getCurators(API_KEY);
            Curator curators = new Curator();

            Log.d(TAG, "BackgroundTask doInBackground: begin to query.");

            try {
                curators = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return curators;
        }

        @Override
        protected void onPostExecute(Curator curators) {
            Log.d(TAG, "BackgroundTask onPostExecute: query finished.");
            Log.d(TAG, "BackgroundTask onPostExecute: the size of dataset is " + curators.dataset.size());

            tv_content.setText("title: " + curators.title + "\n");
            tv_content.setText("total: " + curators.total + "\n\n");
            for (Curator.Dataset dataset : curators.dataset) {
                tv_content.setText(tv_content.getText() + dataset.curator_title +
                        " - " + dataset.curator_tagline + "\n");
            }
        }
    }
}
