package cn.haixiao.ch2;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DoOpeartorDemo {

  public static void main(String[] args) {
    Observable.just("Hello")
        .doOnNext(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            System.out.println("doOnNext: " + s);
          }
        })
        .doAfterNext(new Consumer<String>() {
          @Override public void accept(String s) throws Exception {
            System.out.println("doAfterNext: " + s);
          }
        })
        .doOnComplete(new Action() {
          @Override public void run() throws Exception {
            System.out.println("doOnComplete.");
          }
        }).doOnSubscribe(new Consumer<Disposable>() {
      @Override public void accept(Disposable disposable) throws Exception {
        System.out.println("doOnSubscribe:");
      }
    }).subscribe(new Consumer<String>() {
      @Override public void accept(String s) throws Exception {
        System.out.println("receive msg: " + s);
      }
    });
  }
}
