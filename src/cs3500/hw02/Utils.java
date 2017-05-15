package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by josh_jpeg on 5/15/17.
 */
public class Utils {
  public static <T> boolean noDuplicates(List<T> list) {
    if (list.size() < 2) {
      return true;
    }
    List<T> tempList = new ArrayList<>();
    for (T t : list) {
      tempList.add(t);
    }
    while (tempList.size() > 0) {
      T item = tempList.remove(0);
      for (T t : tempList) {
        if (t.equals(item)) {
          return false;
        }
      }
    }
    return true;
  }

  public static <T> List<T> shuffle(List<T> list) {
    if (list.size() > 1) {
      List<T> tempList = new ArrayList<>();
      for (T t : list) {
        tempList.add(t);
      }
      list.clear();
      while (tempList.size() > 0) {
        int index = new Random().nextInt(tempList.size());
        list.add(tempList.remove(index));
      }
    }

    return list;
  }

  public static <T> String listToString(List<T> list) {
    String str = "";
    if (list != null) {
      for (T item : list) {
        str += " " + item.toString();
        if (list.indexOf(item) < list.size() - 1) {
          str += ",";
        }
      }
    }
    return str;
  }
}
