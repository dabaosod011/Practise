package cn.haixiao.ch2;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class SubjectDemo {
  public static void main(String[] args) {

    AsyncSubject<String> asyncSubject = AsyncSubject.create();
    asyncSubject.onNext("async message 1");
    asyncSubject.onNext("async message 2");
    //asyncSubject.onComplete();
    asyncSubject.subscribe(System.out::println, Throwable::printStackTrace, System.out::println);
    asyncSubject.onNext("async message 3");
    asyncSubject.onNext("async message 4");
    asyncSubject.onComplete();

    BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("default message");
    behaviorSubject.onNext("behavior message 1");
    behaviorSubject.subscribe(System.out::println, Throwable::printStackTrace, System.out::println);
    //behaviorSubject.onNext("behavior message 1");
    behaviorSubject.onNext("behavior message 2");
    behaviorSubject.onComplete();

    //ReplaySubject<String> replaySubject = ReplaySubject.create();
    ReplaySubject<String> replaySubject = ReplaySubject.createWithSize(1);
    replaySubject.onNext("replay message 1");
    replaySubject.onNext("replay message 2");
    replaySubject.onNext("replay message 3");
    replaySubject.subscribe(System.out::println, Throwable::printStackTrace, System.out::println);
    replaySubject.onNext("replay message 4");
    replaySubject.onComplete();

    PublishSubject<String> publishSubject = PublishSubject.create();
    publishSubject.onNext("publish message 1");
    publishSubject.onNext("publish message 2");
    publishSubject.subscribe(System.out::println, Throwable::printStackTrace, System.out::println);
    publishSubject.onNext("publish message 3");
    publishSubject.onNext("publish message 4");
    publishSubject.onComplete();
  }
}
