package com.markeloff.chap2;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.TimeUnit;

import static com.markeloff.utils.TimeUtil.sleep;

public class Demo6 {

  public void demo() {
    Observable.range(2, 10).subscribe(i -> System.out.println("[1] received: " + i));

    Observable.intervalRange(1, 10, 2, 3, TimeUnit.SECONDS)
        .subscribe(i -> System.out.println("[2] received: " + i));

    Observable.interval(3, TimeUnit.SECONDS)
        .subscribe(s -> System.out.println("[3] received: " + s));

    /***
     *    interval observable is cold or hot ???????
     Observable<Long> source1 = Observable.interval(1, TimeUnit.SECONDS);
     source1.subscribe(l -> System.out.println("Observer 1: " + l));
     sleep(5 * 1000);
     source1.subscribe(l -> System.out.println("Observer 2: " + l));
     */

    ConnectableObservable<Long> source2 = Observable.interval(1, TimeUnit.SECONDS).publish();
    source2.subscribe(l -> System.out.println("Observer 3: " + l));
    source2.connect();
    sleep(5 * 1000);
    source2.subscribe(l -> System.out.println("Observer 4: " + l));
  }
}
