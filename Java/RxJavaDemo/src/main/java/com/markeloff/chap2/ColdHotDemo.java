package com.markeloff.chap2;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class ColdHotDemo {

  private List<String> list = Arrays.asList("A", "BB", "CCC", "DDDD", "EEEEE", "FFFFFF", "GGGGGGG");

  public void coldDemo() {
    Observable<String> source = Observable.fromIterable(list);

    source.map(s -> s.length())
        .filter(i -> i >= 2)
        .subscribe(s -> System.out.println("first received: " + s));

    source.subscribe(s -> System.out.println("second received: " + s));
  }
}
