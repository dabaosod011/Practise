package com.markeloff.chap2;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class Demo1 {

    public void demo() {
        Observable<String> strObservable = Observable.just("A", "BB", "CCC", "DDDD", "EEEEE");
        strObservable.subscribe(s -> System.out.println(s));
        strObservable.map(s -> s.length()).subscribe(s -> System.out.println(s));

        Observable<Long> longObservable = Observable.interval(3, TimeUnit.SECONDS);
        longObservable.subscribe(ll -> System.out.println(ll));

    }
}
