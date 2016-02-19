package com.markeloff.rxdemo;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Markeloff on 2016/2/15.
 * hai.xiao@wen-xintech.com
 */
public class RxDemo {
    private final String TAG = getClass().getSimpleName();

    public void rxDemoForFun1() {
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
        Observable<Integer> myArrayObservable = Observable.from(
                new Integer[]{1, 2, 3, 4, 5, 6}
        ); // Emits each item of the array, one at a time

        myArrayObservable.skip(2)
        .filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer %2 == 0;
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
}
