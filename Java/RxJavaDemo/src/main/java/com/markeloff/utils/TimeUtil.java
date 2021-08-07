package com.markeloff.utils;

public class TimeUtil {

  public static void sleep(long millis) {
    // main thread needs to sleep some times, before the application quits
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
