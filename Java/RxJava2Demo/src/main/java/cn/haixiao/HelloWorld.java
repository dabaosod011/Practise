package cn.haixiao;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HelloWorld {
  public static void main(String[] args) {
    Flowable.just("Hello world").subscribe(System.out::println);

    Flowable<Integer> flowable = Flowable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .map(v -> v * v)
        .filter(v -> v % 2 == 0);
    flowable.subscribe(System.out::println);

    /*Observable.create(emitter -> {
      while (!emitter.isDisposed()) {
        long time = System.currentTimeMillis();
        emitter.onNext(time);
        if (time % 2 == 0) {
          emitter.onError(new IllegalStateException("Odd millisecond!"));
          break;
        }
      }
    }).subscribe(System.out::println, Throwable::printStackTrace);*/

    System.out.println("============================");
    Flowable.range(1, 10)
        .observeOn(Schedulers.computation())
        .map(v -> v * v)
        .blockingSubscribe(System.out::println);

    //  Parallel processing
    System.out.println("============================");
    Flowable.range(1, 10)
        .flatMap(v ->
            Flowable.just(v)
                .subscribeOn(Schedulers.computation())
                .map(w -> w * w)
        )
        .blockingSubscribe(System.out::println);

    System.out.println("============================");
    Flowable.range(1, 10)
        .parallel()
        .runOn(Schedulers.computation())
        .map(v -> v * v)
        .sequential()
        .blockingSubscribe(System.out::println);
  }
}
