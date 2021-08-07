package cn.haixiao.cache;

import cn.haixiao.bean.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {

  private Map<String, User> userMap;

  //// singleton
  private static UserCache ourInstance = new UserCache();

  public static UserCache getInstance() {
    return ourInstance;
  }

  private UserCache() {
    userMap = new ConcurrentHashMap<String, User>();
  }

  //////
  public void put(String key, User value) {
    userMap.put(key, value);
  }

  public User get(Object key) {
    return userMap.get(key);
  }
}
