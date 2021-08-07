package com.markeloff.chap2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Demo3 {

  public void demo() {

    Observable<String> source = Observable.just("A", "BB", "CCC", "DDDD", "EEEEE");
    source.map(s -> s.length()).filter(i -> i >= 2).subscribe(new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Integer i) {
        System.out.println("received: " + i);
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onComplete() {
        System.out.println("done!");
      }
    });

    source.map(s -> s.length())
        .filter(i -> i >= 2)
        .subscribe(
            i -> System.out.println("received: " + i),
            Throwable::printStackTrace,
            () -> System.out.println("done!")
        );
  }
}
