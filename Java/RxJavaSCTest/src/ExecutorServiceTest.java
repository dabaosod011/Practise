import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

  private static Callable<String> callable(String result, long sleepSeconds) {
    return () -> {
      TimeUnit.SECONDS.sleep(sleepSeconds);
      return result;
    };
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
    ScheduledFuture<?> future = executor.scheduleWithFixedDelay(task, 0,2, TimeUnit.SECONDS);

    TimeUnit.MILLISECONDS.sleep(1337);

    long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
    System.out.println("Remaining Delay: " + remainingDelay + "ms");
  }
}
