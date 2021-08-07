public class Test {

  private String anyObject = new String();

  public void service() {
    synchronized (anyObject) {
      System.out.println(
          Thread.currentThread().getName() + " begin: " + System.currentTimeMillis());
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName() + " end: " + System.currentTimeMillis());
    }
  }

  synchronized public void service2() {
    System.out.println(Thread.currentThread().getName() + " begin: " + System.currentTimeMillis());
  }

  public static void main(String[] args) {
    Test test = new Test();
    new Thread(() -> test.service(), "Thread-A").start();
    new Thread(() -> test.service2(), "Thread-B").start();
  }
}