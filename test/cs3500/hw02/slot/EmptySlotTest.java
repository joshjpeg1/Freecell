package cs3500.hw02.slot;

import cs3500.hw02.PileType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/** Tests for {@link EmptySlot}. */
public class EmptySlotTest {
  // Tests for the equals() method
  @Test
  public void equalsSameObject() {
    EmptySlot empty = new EmptySlot();
    assertEquals(empty, empty);
  }

  @Test
  public void equalsDiffObjectType() {
    CardSlot card = new CardSlot(CardValue.SIX, CardSuit.SPADES);
    assertNotEquals(new EmptySlot(), card);
  }

  @Test
  public void equalsSameCard() {
    assertEquals(new EmptySlot(), new EmptySlot());
  }

  // Tests for the hashCode() method
  @Test
  public void hashCodeTest() {
    assertEquals(0, new EmptySlot().hashCode());
  }

  // Tests for the toString() method
  @Test
  public void toStringTest() {
    assertEquals("", new EmptySlot().toString());
  }

  // Tests for the moveTo() method
  @Test(expected = IllegalArgumentException.class)
  public void moveToNullISlot() {
    new EmptySlot().moveTo(null, PileType.CASCADE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToNullPileType() {
    new EmptySlot().moveTo(new EmptySlot(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToBothNull() {
    new EmptySlot().moveTo(null, null);
  }

  @Test
  public void moveToEmpty() {
    assertTrue(new EmptySlot().moveTo(new EmptySlot(), PileType.CASCADE));
  }

  @Test
  public void moveToCard() {
    assertTrue(new EmptySlot().moveTo(new CardSlot(CardValue.ACE,
        CardSuit.CLUBS), PileType.CASCADE));
  }

  // Tests for the moveFrom() method
  @Test(expected = IllegalArgumentException.class)
  public void moveFromNullCardSlot() {
    new EmptySlot().moveFrom(null, PileType.CASCADE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFromNullPileType() {
    new EmptySlot().moveFrom(new CardSlot(CardValue.ACE, CardSuit.CLUBS), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFromBothNull() {
    new EmptySlot().moveFrom(null, null);
  }

  @Test
  public void moveFromCascade() {
    assertTrue(new EmptySlot().moveFrom(new CardSlot(CardValue.ACE,
        CardSuit.CLUBS), PileType.CASCADE));
  }

  @Test
  public void moveFromOpen() {
    assertTrue(new EmptySlot().moveFrom(new CardSlot(CardValue.ACE,
        CardSuit.CLUBS), PileType.OPEN));
  }

  @Test
  public void moveFromFoundationNotAce() {
    assertFalse(new EmptySlot().moveFrom(new CardSlot(CardValue.TWO,
        CardSuit.CLUBS), PileType.FOUNDATION));
  }

  @Test
  public void moveFromFoundationAce() {
    assertTrue(new EmptySlot().moveFrom(new CardSlot(CardValue.ACE,
        CardSuit.CLUBS), PileType.FOUNDATION));
  }
}
