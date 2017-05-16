package cs3500.hw02;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** Tests for {@link Utils}. */
public class UtilsTest {
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
}
