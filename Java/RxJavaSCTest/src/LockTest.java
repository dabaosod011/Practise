import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class LockTest {

  private int count = 0;

  private void increment() {
    count = count + 1;
  }

  private void test() {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    IntStream.range(0, 10000).forEach(i -> executor.submit(this::increment));
    ConcurrentUtils.stop(executor);

    System.out.println(count);
  }

  public static void main(String[] args) {

  }
}

