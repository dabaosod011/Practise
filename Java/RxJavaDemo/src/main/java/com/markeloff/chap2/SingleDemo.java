package com.markeloff.chap2;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import static com.markeloff.utils.TimeUtil.sleep;

public class SingleDemo {

  public void demo() {
    Single.just("ABCDEFGHIJKLMN")
        .map(s -> s.substring(0, 5))
        .subscribe(s -> System.out.println("Received: " + s));

    Maybe<Integer> have = Maybe.just(100);
    have.subscribe(i -> System.out.println("Maybe received: " + i),
        Throwable::printStackTrace, () -> System.out.println("have done!"));

    Maybe<Integer> empty = Maybe.empty();
    empty.subscribe(i -> System.out.println("Maybe received: " + i),
        Throwable::printStackTrace, () -> System.out.println("empty done!"));

    Completable.fromRunnable(() -> runProcess())
        .subscribe(() -> System.out.println("Completable Done!"));
  }

  private void runProcess() {
    sleep(5 * 1000);
  }
}
