package cs3500.hw02;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/** Tests for {@link Utils}s. */
public class ListUtilsTest {
  @Test
  public void noDuplicatesExample() {
    assertTrue(Utils.noDuplicates(new ArrayList<Integer>()));
    assertTrue(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2))));
    assertTrue(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 3))));
    assertFalse(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 2))));
    assertFalse(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 3, 2))));
    assertTrue(Utils.noDuplicates(new ArrayList<>(Arrays.asList(2, 3, 4))));
  }

  @Test
  public void listToStringNull() {
    assertEquals("", Utils.listToString(null));
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
