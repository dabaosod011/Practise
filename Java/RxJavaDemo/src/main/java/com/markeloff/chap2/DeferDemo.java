package com.markeloff.chap2;

import io.reactivex.Observable;

public class DeferDemo {

  private static int start = 1;
  private static int count = 10;

  public void demo() {
    Observable<Integer> source = Observable.defer(() -> Observable.range(start, count));

    source.subscribe(i -> System.out.println("[1] received: " + i));
    count = 20;
    source.subscribe(i -> System.out.println("[2] received: " + i));

    Observable.fromCallable(() -> 1 / 0).subscribe(i -> System.out.println("Received: " + i),
        e -> System.out.println("error happened: " + e));
  }
}
