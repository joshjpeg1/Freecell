package cs3500.hw02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A utilities class for use with generic {@code List}s.
 */
public class Utils {
  /**
   * Checks if a {@code List} contains duplicate items.
   *
   * @param list       a generic list of items
   * @return true if there are no duplicates; false otherwise
   */
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

  /**
   * Shuffles all items within a {@code List} and returns the shuffled list.
   *
   * @param list       a generic list of items
   * @return a shuffled list
   */
  public static <T> List<T> shuffle(List<T> list) {
    if (list.size() > 1) {
      List<T> shuffledList = new ArrayList<>();
      List<T> tempList = new ArrayList<>();
      for (T t : list) {
        tempList.add(t);
      }
      while (tempList.size() > 0) {
        int index = new Random().nextInt(tempList.size());
        shuffledList.add(tempList.remove(index));
      }
      return shuffledList;
    }
    return list;
  }

  /**
   * Returns the given {@code List}, without any of the items in the given filter {@code List}.
   *
   * @param list       a generic list of items
   * @param filters    a generic list of items to be filtered
   * @return a filtered list
   */
  public static <T> List<T> filterList(List<T> list, List<T> filters) {
    List<T> finalList = new ArrayList<>();
    for (T item : list) {
      if (!filters.contains(item)) {
        finalList.add(item);
      }
    }
    return finalList;
  }

  /**
   * Returns a string representation of a {@code List}.
   *
   * @param list       a generic list of items
   * @return a string representation of the given {@code List}
   */
  public static <T> String listToString(List<T> list) {
    String str = "";
    for (T item : list) {
      str += " " + item.toString();
      if (list.indexOf(item) < list.size() - 1) {
        str += ",";
      }
    }
    return str;
  }

  /**
   * Returns the last element in a {@code List}.
   *
   * @param list       a generic list of items
   * @return the last element
   * @throws IndexOutOfBoundsException if the given {@code List} is empty
   */
  public static <T> T getLast(List<T> list) throws IndexOutOfBoundsException {
    if (list.size() == 0) {
      throw new IndexOutOfBoundsException("Can't get the last item of an empty list.");
    }
    return list.get(list.size() - 1);
  }

  /**
   * Returns the last element in a {@code List}.
   *
   * @param list       a generic list of items
   * @return the last element
   * @throws IndexOutOfBoundsException if the given {@code List} is empty
   */
  public static <T> List<T> reverse(List<T> list) {
    if (list.size() > 1) {
      List<T> copy = new ArrayList<>();
      for (T item : list) {
        copy.add(0, item);
      }
      return copy;
    }
    return list;
  }
}
