package cs3500.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/** Tests for {@link Utils}. */
public class UtilsTest {
  // Tests for the noDuplicates() method
  @Test(expected = IllegalArgumentException.class)
  public void noDuplicatesNull() {
    Utils.noDuplicates(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noDuplicatesContainsNull() {
    Utils.noDuplicates(new ArrayList<>(Arrays.asList("a", null, "b")));
  }

  @Test
  public void noDuplicatesEmpty() {
    assertTrue(Utils.noDuplicates(new ArrayList<Integer>()));
  }

  @Test
  public void noDuplicatesOneElem() {
    assertTrue(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2))));
  }

  @Test
  public void noDuplicatesTwoElemNoDupe() {
    assertTrue(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 3))));
  }

  @Test
  public void noDuplicatesTwoElemDupe() {
    assertFalse(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 2))));
  }

  @Test
  public void noDuplicatesThreeElemNoDupe() {
    assertTrue(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 3, 4))));
  }

  @Test
  public void noDuplicatesThreeElemDupe() {
    assertFalse(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 3, 2))));
  }

  // Tests for the shuffle() method
  @Test(expected = IllegalArgumentException.class)
  public void shuffleNull() {
    Utils.shuffle(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shuffleContainsNull() {
    Utils.shuffle(new ArrayList<>(Arrays.asList("a", null, "b")));
  }

  @Test
  public void shuffleEmpty() {
    ArrayList<Integer> list = new ArrayList<>();
    assertEquals(list, Utils.shuffle(list));
  }

  @Test
  public void shuffleOneElem() {
    ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1));
    assertEquals(list, Utils.shuffle(list));
  }

  @Test
  public void shuffleMultElemContainsAll() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
    List<Integer> shuffList = Utils.shuffle(list);
    for (Integer i : list) {
      assertTrue(shuffList.contains(i));
      shuffList.remove(i);
    }
    assertEquals(0, shuffList.size());
  }

  @Test
  public void shuffleMultElemSameSize() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
    assertEquals(list.size(), Utils.shuffle(list).size());
  }

  // Tests for the filterList() method
  @Test(expected = IllegalArgumentException.class)
  public void filterListNullList() {
    Utils.filterList(null, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterListNullFilt() {
    Utils.filterList(new ArrayList<>(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterListBothNull() {
    Utils.filterList(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterListListContainsNull() {
    List<String> list = new ArrayList<>(Arrays.asList("a", null, "b"));
    List<String> filter = new ArrayList<>(Arrays.asList("a"));
    Utils.filterList(list, filter);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterListFiltContainsNull() {
    List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
    List<String> filter = new ArrayList<>(Arrays.asList("a", null));
    Utils.filterList(list, filter);
  }

  @Test(expected = IllegalArgumentException.class)
  public void filterListBothContainNull() {
    List<String> list = new ArrayList<>(Arrays.asList("a", null, "b"));
    List<String> filter = new ArrayList<>(Arrays.asList("a", null));
    Utils.filterList(list, filter);
  }

  @Test
  public void filterListEmptyNoFilt() {
    List<Integer> list = new ArrayList<>();
    List<Integer> filter = new ArrayList<>();
    assertEquals(list, Utils.filterList(list, filter));
    assertEquals(filter, Utils.filterList(list, filter));
  }

  @Test
  public void filterListEmptyFilter() {
    List<Integer> list = new ArrayList<>();
    List<Integer> filter = new ArrayList<>(Arrays.asList(1, 2, 3));
    assertEquals(list, Utils.filterList(list, filter));
    assertNotEquals(filter, Utils.filterList(list, filter));
  }

  @Test
  public void filterListOneElemNoFilt() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1));
    List<Integer> filter = new ArrayList<>();
    assertEquals(list, Utils.filterList(list, filter));
  }

  @Test
  public void filterListMultElemNoFilt() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> filter = new ArrayList<>();
    assertEquals(list, Utils.filterList(list, filter));
  }

  @Test
  public void filterListOneElemFilterNothing() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1));
    List<Integer> filter = new ArrayList<>(Arrays.asList(4, 5, 6));
    assertEquals(list, Utils.filterList(list, filter));
  }

  @Test
  public void filterListMultElemFilterNothing() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> filter = new ArrayList<>(Arrays.asList(4, 5, 6));
    assertEquals(list, Utils.filterList(list, filter));
  }

  @Test
  public void filterListOneElemFilter() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1));
    List<Integer> filter = new ArrayList<>(Arrays.asList(3, 1, 2));
    assertEquals(new ArrayList<>(), Utils.filterList(list, filter));
  }

  @Test
  public void filterListMultElemFilterSome() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> filter = new ArrayList<>(Arrays.asList(4, 2, 6));
    assertEquals(new ArrayList<>(Arrays.asList(1, 3)),
        Utils.filterList(list, filter));
  }

  @Test
  public void filterListMultElemFilterAll() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> filter = new ArrayList<>(Arrays.asList(3, 1, 2));
    assertEquals(new ArrayList<>(), Utils.filterList(list, filter));
  }

  @Test
  public void filterListSameList() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    assertEquals(new ArrayList<>(), Utils.filterList(list, list));
  }

  // Tests for the listToString() method
  @Test(expected = IllegalArgumentException.class)
  public void listToStringNullList() {
    Utils.listToString(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void listToStringContainsNull() {
    Utils.listToString(new ArrayList<>(Arrays.asList("a", null, "b")));
  }

  @Test
  public void listToStringEmpty() {
    assertEquals("", Utils.listToString(new ArrayList<Integer>()));
  }

  @Test
  public void listToStringOneElem() {
    assertEquals(" 2", Utils.listToString(new ArrayList<>(Arrays.asList(2))));
  }

  @Test
  public void listToStringTwoElem() {
    assertEquals(" 2, 3", Utils.listToString(new ArrayList<>(Arrays.asList(2, 3))));
  }

  @Test
  public void listToStringMultElem() {
    assertEquals(" 2, 3, 4", Utils.listToString(new ArrayList<>(Arrays.asList(2, 3, 4))));
  }

  // Tests for the getLast() method
  @Test(expected = IllegalArgumentException.class)
  public void getLastNull() {
    Utils.getLast(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getLastContainsNull() {
    Utils.getLast(new ArrayList<>(Arrays.asList("a", null, "b")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getLastEmpty() {
    Utils.getLast(new ArrayList<>());
  }

  @Test
  public void getLastOneElem() {
    assertEquals((Integer) 1, Utils.getLast(new ArrayList<>(Arrays.asList(1))));
  }

  @Test
  public void getLastMultElem() {
    assertEquals((Integer) 3, Utils.getLast(new ArrayList<>(Arrays.asList(1, 2, 3))));
  }

  // Tests for the reverse() method
  @Test(expected = IllegalArgumentException.class)
  public void reverseNull() {
    Utils.reverse(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void reverseContainsNull() {
    Utils.reverse(new ArrayList<>(Arrays.asList("a", null, "b")));
  }

  @Test
  public void reverseEmpty() {
    List<Integer> list = new ArrayList<>();
    assertEquals(list, Utils.reverse(list));
  }

  @Test
  public void reverseOneElem() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1));
    assertEquals(list, Utils.reverse(list));
  }

  @Test
  public void reverseMultElem() {
    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    List<Integer> rev = new ArrayList<>(Arrays.asList(3, 2, 1));
    assertEquals(rev, Utils.reverse(list));
  }
}
