import java.util.concurrent.TimeUnit;

public class RunnableTest {

  private static Runnable task = () -> {
    String threadName = Thread.currentThread().getName();
    System.out.println("Foo " + threadName);

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Bar " + threadName);
  };

  public static void main(String[] args) {
    Thread thread = new Thread(task);
    thread.start();

    task.run();

    System.out.println("Done!");
  }
}
