package cn.haixiao;

import cn.haixiao.bean.User;
import cn.haixiao.cache.UserCache;

public class DemoTest {
  public static void main(String[] args) {
    User user = new User("alice", 18, false, 160, 60);
    UserCache.getInstance().put(user.getId(), user);

    System.out.println(UserCache.getInstance().get(user.getId()).toString());
  }
}
