package cn.haixiao.ch2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class ColdObservableDemo {

  public static void main(String[] args) {

    Consumer<Long> consumer1 = new Consumer<Long>() {
      @Override public void accept(Long aLong) throws Exception {
        System.out.println("consumer1: " + aLong);
      }
    };

    Consumer<Long> consumer2 = new Consumer<Long>() {
      @Override public void accept(Long aLong) throws Exception {
        System.out.println("consumer2: " + aLong);
      }
    };

    Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
      @Override public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
        Observable.interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
            .take(Integer.MAX_VALUE)
            .subscribe(emitter::onNext);
      }
    }).observeOn(Schedulers.newThread());

    observable.subscribe(consumer1);
    observable.subscribe(consumer2);

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
