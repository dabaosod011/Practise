public class TestService {

  private String username = "AA";
  private String password = "aa";

  /*synchronized*/ public void getValue() {
    System.out.println(Thread.currentThread().getName() + " : " + username + " " + password);
  }

  synchronized public void setValue(String username, String password) {
    this.username = username;
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.password = password;
  }

  public static void main(String[] args) throws InterruptedException {
    TestService service = new TestService();

    Thread thread1 = new Thread(() -> service.setValue("BB", "bb"), "Thread-A");
    thread1.start();
    Thread.sleep(200);
    Thread thread2 = new Thread(service::getValue, "Thread-B");
    thread2.start();
  }
}
