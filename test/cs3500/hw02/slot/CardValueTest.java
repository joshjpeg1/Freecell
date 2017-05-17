package cs3500.hw02.slot;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/** Tests for {@link CardValue}. */
public class CardValueTest {
  // Tests for the toString() method
  @Test
  public void toStringSingleDigit() {
    assertEquals("2", CardValue.TWO.toString());
  }

  @Test
  public void toStringDoubleDigit() {
    assertEquals("10", CardValue.TEN.toString());
  }

  @Test
  public void toStringAce() {
    assertEquals("A", CardValue.ACE.toString());
  }

  @Test
  public void toStringJack() {
    assertEquals("J", CardValue.JACK.toString());
  }

  @Test
  public void toStringQueen() {
    assertEquals("Q", CardValue.QUEEN.toString());
  }

  @Test
  public void toStringKing() {
    assertEquals("K", CardValue.KING.toString());
  }

  // Tests for the getDifference() method
  @Test
  public void getDifferenceFaceFace() {
    assertEquals(1, CardValue.KING.getDifference(CardValue.QUEEN));
  }

  @Test
  public void getDifferenceFaceTen() {
    assertEquals(2, CardValue.QUEEN.getDifference(CardValue.TEN));
  }

  @Test
  public void getDifferenceFaceNum() {
    assertEquals(10, CardValue.KING.getDifference(CardValue.THREE));
  }

  @Test
  public void getDifferenceFaceAce() {
    assertEquals(10, CardValue.JACK.getDifference(CardValue.ACE));
  }

  @Test
  public void getDifferenceNumNum() {
    assertEquals(5, CardValue.SEVEN.getDifference(CardValue.TWO));
  }

  @Test
  public void getDifferenceNumAce() {
    assertEquals(6, CardValue.SEVEN.getDifference(CardValue.ACE));
  }
}
