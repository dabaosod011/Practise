package cn.haixiao.bean;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
  @SerializedName("user_id") private String id;
  @SerializedName("name") private String name;
  @SerializedName("age") private int age;
  @SerializedName("is_married") private boolean isMarried;
  @SerializedName("height") private int height;
  @SerializedName("weight") private int weight;

  public User() {
    this(null, -1, false, 0, 0);
  }

  public User(String name, int age, boolean isMarried, int height, int weight) {
    long time = new Date().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    setId("id_" + sdf.format(time));

    this.name = name;
    this.age = age;
    this.isMarried = isMarried;
    this.height = height;
    this.weight = weight;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public boolean isMarried() {
    return isMarried;
  }

  public void setMarried(boolean married) {
    isMarried = married;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  @Override public String toString() {
    return new GsonBuilder().create().toJson(this, User.class);
  }
}
