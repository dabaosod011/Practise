public class DeanLockThreadDemo implements Runnable {

  public String username;
  public Object locak1 = new Object();
  public Object locak2 = new Object();

  public void setFlag(String username) {
    this.username = username;
  }

  @Override
  public void run() {
    if (username.equals("a")) {
      synchronized (locak1) {
        System.out.println("username:" + username);
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (locak2) {
          System.out.println("按lock1 -> lock2执行");
        }
      }
    }

    if (username.equals("b")) {
      synchronized (locak2) {
        System.out.println("username:" + username);
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (locak1) {
          System.out.println("按lock2 -> lock1执行");
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    DeanLockThreadDemo dealThread = new DeanLockThreadDemo();

    dealThread.setFlag("a");
    Thread threadA = new Thread(dealThread);
    threadA.start();

    Thread.sleep(100);

    dealThread.setFlag("b");
    Thread threadB = new Thread(dealThread);
    threadB.start();
  }
}
