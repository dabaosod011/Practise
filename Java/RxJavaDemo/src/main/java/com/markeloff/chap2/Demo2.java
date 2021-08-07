package com.markeloff.chap2;

import io.reactivex.Observable;

public class Demo2 {

    public void demo() {
        Observable<String> strObservable = Observable.create(emitter -> {
            try {
                emitter.onNext("A");
                emitter.onNext("BB");
                emitter.onNext("CCC");
                emitter.onNext("DDDD");
                emitter.onNext("EEEEE");

                emitter.onComplete();
            } catch (Throwable e) {
                emitter.onError(e);
            }
        });

        strObservable.map(s -> s.length()).filter(i -> i >= 2).subscribe(i -> System.out.println("received: " + i));
    }

}
