package cs3500.hw02.slot;

import cs3500.hw02.PileType;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** Tests for {@link CardSlot}. */
public class CardSlotTest {
  // Tests for the equals() method
  @Test
  public void equalsSameObject() {
    CardSlot card = new CardSlot(CardValue.SIX, CardSuit.SPADES);
    assertEquals(card, card);
  }

  @Test
  public void equalsDiffObjectType() {
    CardSlot card = new CardSlot(CardValue.SIX, CardSuit.SPADES);
    assertNotEquals(card, new EmptySlot());
  }

  @Test
  public void equalsSameCard() {
    assertEquals(new CardSlot(CardValue.SIX, CardSuit.SPADES),
        new CardSlot(CardValue.SIX, CardSuit.SPADES));
  }

  @Test
  public void equalsDiffValue() {
    assertNotEquals(new CardSlot(CardValue.SIX, CardSuit.SPADES),
        new CardSlot(CardValue.SEVEN, CardSuit.SPADES));
  }

  @Test
  public void equalsDiffSuit() {
    assertNotEquals(new CardSlot(CardValue.SIX, CardSuit.SPADES),
        new CardSlot(CardValue.SIX, CardSuit.CLUBS));
  }

  @Test
  public void equalsDiffCard() {
    assertNotEquals(new CardSlot(CardValue.SIX, CardSuit.SPADES),
        new CardSlot(CardValue.SEVEN, CardSuit.CLUBS));
  }

  // Tests for the hashCode() method
  @Test
  public void hashCodeSingleDigitValue() {
    assertEquals(21, new CardSlot(CardValue.TWO, CardSuit.CLUBS).hashCode());
  }

  @Test
  public void hashCodeDoubleDigitValue() {
    assertEquals(121, new CardSlot(CardValue.QUEEN, CardSuit.CLUBS).hashCode());
  }

  @Test
  public void hashCodeClubs() {
    assertEquals(61, new CardSlot(CardValue.SIX, CardSuit.CLUBS).hashCode());
  }

  @Test
  public void hashCodeDiamonds() {
    assertEquals(62, new CardSlot(CardValue.SIX, CardSuit.DIAMONDS).hashCode());
  }

  @Test
  public void hashCodeSpades() {
    assertEquals(63, new CardSlot(CardValue.SIX, CardSuit.SPADES).hashCode());
  }

  @Test
  public void hashCodeHearts() {
    assertEquals(64, new CardSlot(CardValue.SIX, CardSuit.HEARTS).hashCode());
  }

  // Tests for the toString() method
  @Test
  public void toStringAce() {
    ISlot card = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    assertEquals("A♣", card.toString());
  }

  @Test
  public void toStringSingleDigitNumber() {
    ISlot card = new CardSlot(CardValue.TWO, CardSuit.CLUBS);
    assertEquals("2♣", card.toString());
  }

  @Test
  public void toStringDoubleDigitNumber() {
    ISlot card = new CardSlot(CardValue.TEN, CardSuit.CLUBS);
    assertEquals("10♣", card.toString());
  }

  @Test
  public void toStringJack() {
    ISlot card = new CardSlot(CardValue.JACK, CardSuit.CLUBS);
    assertEquals("J♣", card.toString());
  }

  @Test
  public void toStringQueen() {
    ISlot card = new CardSlot(CardValue.QUEEN, CardSuit.CLUBS);
    assertEquals("Q♣", card.toString());
  }

  @Test
  public void toStringKing() {
    ISlot card = new CardSlot(CardValue.KING, CardSuit.CLUBS);
    assertEquals("K♣", card.toString());
  }

  @Test
  public void toStringClubs() {
    ISlot card = new CardSlot(CardValue.THREE, CardSuit.CLUBS);
    assertEquals("3♣", card.toString());
  }

  @Test
  public void toStringHearts() {
    ISlot card = new CardSlot(CardValue.ACE, CardSuit.HEARTS);
    assertEquals("A♥", card.toString());
  }

  @Test
  public void toStringDiamonds() {
    ISlot card = new CardSlot(CardValue.TEN, CardSuit.DIAMONDS);
    assertEquals("10♦", card.toString());
  }

  @Test
  public void toStringSpades() {
    ISlot card = new CardSlot(CardValue.KING, CardSuit.SPADES);
    assertEquals("K♠", card.toString());
  }

  // Tests for the moveTo() method
  @Test
  public void moveToEmpty() {
    CardSlot card = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    ISlot empty = new EmptySlot();
    assertEquals(card.moveTo(empty, PileType.CASCADE), empty.moveFrom(card, PileType.CASCADE));
  }

  @Test
  public void moveToCard() {
    CardSlot card1 = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    ISlot card2 = new CardSlot(CardValue.EIGHT, CardSuit.DIAMONDS);
    assertEquals(card1.moveTo(card2, PileType.CASCADE), card2.moveFrom(card1, PileType.CASCADE));
  }

  // Tests for the moveFrom() method
  @Test
  public void moveFromSameValueCascade() {
    CardSlot card1 = new CardSlot(CardValue.QUEEN, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.QUEEN, CardSuit.HEARTS);
    assertFalse(card2.moveFrom(card1, PileType.CASCADE));
  }

  @Test
  public void moveFromSameColorCascade() {
    CardSlot card1 = new CardSlot(CardValue.QUEEN, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.KING, CardSuit.CLUBS);
    assertFalse(card2.moveFrom(card1, PileType.CASCADE));
  }

  @Test
  public void moveFromSameCardCascade() {
    CardSlot card1 = new CardSlot(CardValue.QUEEN, CardSuit.SPADES);
    assertFalse(card1.moveFrom(card1, PileType.CASCADE));
  }

  @Test
  public void moveFromHighToLowCascade() {
    CardSlot card1 = new CardSlot(CardValue.KING, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.QUEEN, CardSuit.HEARTS);
    assertFalse(card2.moveFrom(card1, PileType.CASCADE));
  }

  @Test
  public void moveFromLowToHighCascade() {
    CardSlot card1 = new CardSlot(CardValue.QUEEN, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.KING, CardSuit.HEARTS);
    assertTrue(card2.moveFrom(card1, PileType.CASCADE));
  }

  @Test
  public void moveFromSameValueFoundation() {
    CardSlot card1 = new CardSlot(CardValue.THREE, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.THREE, CardSuit.CLUBS);
    assertFalse(card2.moveFrom(card1, PileType.FOUNDATION));
  }

  @Test
  public void moveFromDifferentSuitFoundation() {
    CardSlot card1 = new CardSlot(CardValue.FOUR, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.THREE, CardSuit.CLUBS);
    assertFalse(card2.moveFrom(card1, PileType.FOUNDATION));
  }

  @Test
  public void moveFromSameCardFoundation() {
    CardSlot card1 = new CardSlot(CardValue.THREE, CardSuit.SPADES);
    assertFalse(card1.moveFrom(card1, PileType.FOUNDATION));
  }

  @Test
  public void moveFromLowToHighFoundation() {
    CardSlot card1 = new CardSlot(CardValue.THREE, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.FOUR, CardSuit.SPADES);
    assertFalse(card2.moveFrom(card1, PileType.FOUNDATION));
  }

  @Test
  public void moveFromHighToLowFoundation() {
    CardSlot card1 = new CardSlot(CardValue.FOUR, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.THREE, CardSuit.SPADES);
    assertTrue(card2.moveFrom(card1, PileType.FOUNDATION));
  }

  @Test
  public void moveFromOpen() {
    CardSlot card1 = new CardSlot(CardValue.QUEEN, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.KING, CardSuit.HEARTS);
    assertFalse(card2.moveFrom(card1, PileType.OPEN));
  }

  // Tests for the oppositeColor() method
  @Test
  public void oppositeColorSameSuit() {
    CardSlot card1 = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    CardSlot card2 = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    assertFalse(card1.oppositeColor(card2));
  }

  @Test
  public void oppositeColorSameColor() {
    CardSlot card1 = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    CardSlot card2 = new CardSlot(CardValue.ACE, CardSuit.SPADES);
    assertFalse(card1.oppositeColor(card2));
  }

  @Test
  public void oppositeColorBlackRed() {
    CardSlot card1 = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    CardSlot card2 = new CardSlot(CardValue.ACE, CardSuit.HEARTS);
    assertTrue(card1.oppositeColor(card2));
  }

  @Test
  public void oppositeColorRedBlack() {
    CardSlot card1 = new CardSlot(CardValue.ACE, CardSuit.SPADES);
    CardSlot card2 = new CardSlot(CardValue.ACE, CardSuit.DIAMONDS);
    assertTrue(card2.oppositeColor(card1));
  }
}
