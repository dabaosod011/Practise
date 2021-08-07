package com.markeloff.chap2;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class ConnectableDemo {

  public void demo() {

    ConnectableObservable<String> source =
        Observable.just("A", "BB", "CCC", "DDDD", "EEEEE").publish();

    source.subscribe(s -> System.out.println("Observer 1: " + s));
    source.map(s -> s.length()).subscribe(i -> System.out.println("Observer 2: " + i));

    source.connect();

    Observable<String> empty = Observable.empty();
    empty.subscribe(System.out::println, Throwable::printStackTrace,
        () -> System.out.println("Done!"));
  }
}
