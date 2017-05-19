package cs3500.hw02.slot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/** Tests for {@link CardSuit}. */
public class CardSuitTest {
  // Tests for the toString() method
  @Test
  public void toStringClubs() {
    assertEquals("♣", CardSuit.CLUBS.toString());
  }

  @Test
  public void toStringDiamonds() {
    assertEquals("♦", CardSuit.DIAMONDS.toString());
  }

  @Test
  public void toStringHearts() {
    assertEquals("♥", CardSuit.HEARTS.toString());
  }

  @Test
  public void toStringSpades() {
    assertEquals("♠", CardSuit.SPADES.toString());
  }

  // Tests for the oppositeColor() method
  @Test(expected = IllegalArgumentException.class)
  public void oppositeColorNull() {
    CardSuit.CLUBS.oppositeColor(null);
  }

  @Test
  public void oppositeColorSameSuit() {
    assertFalse(CardSuit.CLUBS.oppositeColor(CardSuit.CLUBS));
  }

  @Test
  public void oppositeColorSameColor() {
    assertFalse(CardSuit.CLUBS.oppositeColor(CardSuit.SPADES));
  }

  @Test
  public void oppositeColorBlackRed() {
    assertTrue(CardSuit.CLUBS.oppositeColor(CardSuit.HEARTS));
  }

  @Test
  public void oppositeColorRedBlack() {
    assertTrue(CardSuit.DIAMONDS.oppositeColor(CardSuit.SPADES));
  }
}
