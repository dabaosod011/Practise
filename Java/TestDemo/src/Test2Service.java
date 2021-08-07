public class Test2Service {

  private String lock = "123";

  public void service() {
    synchronized (lock) {
      System.out.println(
          Thread.currentThread().getName() + " begin: " + System.currentTimeMillis());
      lock = "456";
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " end: " + System.currentTimeMillis());
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Test2Service test = new Test2Service();
    
    new Thread(() -> test.service(), "Thread-A").start();
    Thread.sleep(50);
    new Thread(() -> test.service(), "Thread-B").start();
  }
}
