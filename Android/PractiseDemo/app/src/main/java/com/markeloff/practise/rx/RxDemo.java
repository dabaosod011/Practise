package com.markeloff.practise.rx;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Markeloff on 2016/3/11.
 */
public class RxDemo {
    private static final String TAG = RxDemo.class.getSimpleName();
    private final String URL_SINA = "http://www.sina.com.cn";
    private final String URL_BAIDU = "http://www.baidu.com";

    public void rxDemoForFun1() {
        Log.d(TAG, "rxDemoForFun1.");
        Observable<String> myObservable = Observable.just("Hello"); // Emits "Hello"

        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error happens!!!");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "In myObserver: get a string : " + s);
            }
        };

        Action1<String> myAction1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "In Action1: get a string : " + s);
            }
        };

        myObservable.subscribe(myObserver);
        myObservable.subscribe(myAction1);
    }

    public void rxDemoForFun2() {
        Log.d(TAG, "rxDemoForFun2.");
        Observable<Integer> myArrayObservable = Observable.from(
                new Integer[]{1, 2, 3, 4, 5, 6}
        ); // Emits each item of the array, one at a time

        myArrayObservable.skip(2)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                }).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.d(TAG, String.valueOf(i));
            }
        });
    }

    public void rxDemoForFun3() {
        Log.d(TAG, "rxDemoForFun3. ");

        Observable<String> fetchFromSina = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = fetchData(URL_SINA);
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                } catch (Exception e) {
                    Log.e(TAG, "call: Exception happens.");
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        fetchFromSina
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call: the length of sina.com.cn is : " + s.length());
                    }
                });

        Observable<String> fetchFromBaidu = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = fetchData(URL_BAIDU);
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                } catch (Exception e) {
                    Log.e(TAG, "call: Exception happens.");
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        fetchFromBaidu
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call: the length of baidu.com is : " + s.length());
                    }
                });

        // Fetch from both simultaneously
        Observable<String> zipped
                = Observable.zip(fetchFromSina, fetchFromBaidu, new Func2<String, String, String>() {
            @Override
            public String call(String sina, String baidu) {
                // Do something with the results of both threads
                return sina.length() + ", " + baidu.length();
            }
        });

        zipped.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call: zipped return result is : " + s);
                    }
                });

        // Emit the results one after another
        Observable<String> concatenated = Observable.concat(fetchFromSina, fetchFromBaidu);
        concatenated.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "call: concatenated return result is : " + s);
                    }
                });
    }

    private String fetchData(String mUrl) {
        Log.d(TAG, "fetchData: url : " + mUrl);
        String content = "Nothing found!";

        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(mUrl)
                    .build();
            Response response = client.newCall(request).execute();
            content = response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "fetchData: Exception happens.");
            e.printStackTrace();
        }

        return content;
    }
}
