package cs3500.hw02.slot;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** Tests for {@link CardSlot}. */
public class CardSlotTest {
  // Tests for the equals() method
  @Test
  public void equalsSameObject() {
    CardSlot card = new CardSlot(CardValue.SIX, CardSuit.SPADES);
    assertTrue(card.equals(card));
  }

  @Test
  public void equalsDiffObjectType() {
    CardSlot card = new CardSlot(CardValue.SIX, CardSuit.SPADES);
    assertFalse(card.equals(15));
  }

  @Test
  public void equalsSameCard() {
    assertTrue(new CardSlot(CardValue.SIX, CardSuit.SPADES)
        .equals(new CardSlot(CardValue.SIX, CardSuit.SPADES)));
  }

  @Test
  public void equalsDiffValue() {
    assertFalse(new CardSlot(CardValue.SIX, CardSuit.SPADES)
        .equals(new CardSlot(CardValue.SEVEN, CardSuit.SPADES)));
  }

  @Test
  public void equalsDiffSuit() {
    assertFalse(new CardSlot(CardValue.SIX, CardSuit.SPADES)
        .equals(new CardSlot(CardValue.SIX, CardSuit.CLUBS)));
  }

  @Test
  public void equalsDiffCard() {
    assertFalse(new CardSlot(CardValue.SIX, CardSuit.SPADES)
        .equals(new CardSlot(CardValue.SEVEN, CardSuit.CLUBS)));
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
    ASlot card = new CardSlot(CardValue.ACE, CardSuit.CLUBS);
    assertEquals("A♣", card.toString());
  }

  @Test
  public void toStringSingleDigitNumber() {
    ASlot card = new CardSlot(CardValue.TWO, CardSuit.CLUBS);
    assertEquals("2♣", card.toString());
  }

  @Test
  public void toStringDoubleDigitNumber() {
    ASlot card = new CardSlot(CardValue.TEN, CardSuit.CLUBS);
    assertEquals("10♣", card.toString());
  }

  @Test
  public void toStringJack() {
    ASlot card = new CardSlot(CardValue.JACK, CardSuit.CLUBS);
    assertEquals("J♣", card.toString());
  }

  @Test
  public void toStringQueen() {
    ASlot card = new CardSlot(CardValue.QUEEN, CardSuit.CLUBS);
    assertEquals("Q♣", card.toString());
  }

  @Test
  public void toStringKing() {
    ASlot card = new CardSlot(CardValue.KING, CardSuit.CLUBS);
    assertEquals("K♣", card.toString());
  }

  @Test
  public void toStringClubs() {
    ASlot card = new CardSlot(CardValue.THREE, CardSuit.CLUBS);
    assertEquals("3♣", card.toString());
  }

  @Test
  public void toStringHearts() {
    ASlot card = new CardSlot(CardValue.ACE, CardSuit.HEARTS);
    assertEquals("A♥", card.toString());
  }

  @Test
  public void toStringDiamonds() {
    ASlot card = new CardSlot(CardValue.TEN, CardSuit.DIAMONDS);
    assertEquals("10♦", card.toString());
  }

  @Test
  public void toStringSpades() {
    ASlot card = new CardSlot(CardValue.KING, CardSuit.SPADES);
    assertEquals("K♠", card.toString());
  }
}
