package cs3500.hw02.slot;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/** Tests for {@link CardSlot}. */
public class CardSlotTest {
  // Tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void constructorValueTooLow() {
    new CardSlot(0, Suit.CLUBS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorValueTooHigh() {
    new CardSlot(14, Suit.CLUBS);
  }

  // Tests for the equals() method
  @Test
  public void equalsSameObject() {
    CardSlot card = new CardSlot(6, Suit.SPADES);
    assertTrue(card.equals(card));
  }

  @Test
  public void equalsDiffObjectType() {
    CardSlot card = new CardSlot(6, Suit.SPADES);
    assertFalse(card.equals(15));
  }

  @Test
  public void equalsSameCard() {
    assertTrue(new CardSlot(6, Suit.SPADES).equals(new CardSlot(6, Suit.SPADES)));
  }

  @Test
  public void equalsDiffValue() {
    assertFalse(new CardSlot(6, Suit.SPADES).equals(new CardSlot(7, Suit.SPADES)));
  }

  @Test
  public void equalsDiffSuit() {
    assertFalse(new CardSlot(6, Suit.SPADES).equals(new CardSlot(6, Suit.CLUBS)));
  }

  @Test
  public void equalsDiffCard() {
    assertFalse(new CardSlot(6, Suit.SPADES).equals(new CardSlot(7, Suit.CLUBS)));
  }

  // Tests for the hashCode() method
  @Test
  public void hashCodeSingleDigitValue() {
    assertEquals(21, new CardSlot(2, Suit.CLUBS).hashCode());
  }

  @Test
  public void hashCodeDoubleDigitValue() {
    assertEquals(121, new CardSlot(CardSlot.QUEEN, Suit.CLUBS).hashCode());
  }

  @Test
  public void hashCodeClubs() {
    assertEquals(61, new CardSlot(6, Suit.CLUBS).hashCode());
  }

  @Test
  public void hashCodeDiamonds() {
    assertEquals(62, new CardSlot(6, Suit.DIAMONDS).hashCode());
  }

  @Test
  public void hashCodeSpades() {
    assertEquals(63, new CardSlot(6, Suit.SPADES).hashCode());
  }

  @Test
  public void hashCodeHearts() {
    assertEquals(64, new CardSlot(6, Suit.HEARTS).hashCode());
  }

  // Tests for the toString() method
  @Test
  public void toStringAce() {
    ASlot card = new CardSlot(CardSlot.ACE, Suit.CLUBS);
    assertEquals("A♣", card.toString());
  }

  @Test
  public void toStringSingleDigitNumber() {
    ASlot card = new CardSlot(2, Suit.CLUBS);
    assertEquals("2♣", card.toString());
  }

  @Test
  public void toStringDoubleDigitNumber() {
    ASlot card = new CardSlot(10, Suit.CLUBS);
    assertEquals("10♣", card.toString());
  }

  @Test
  public void toStringJack() {
    ASlot card = new CardSlot(CardSlot.JACK, Suit.CLUBS);
    assertEquals("J♣", card.toString());
  }

  @Test
  public void toStringQueen() {
    ASlot card = new CardSlot(CardSlot.QUEEN, Suit.CLUBS);
    assertEquals("Q♣", card.toString());
  }

  @Test
  public void toStringKing() {
    ASlot card = new CardSlot(CardSlot.KING, Suit.CLUBS);
    assertEquals("K♣", card.toString());
  }

  @Test
  public void toStringClubs() {
    ASlot card = new CardSlot(3, Suit.CLUBS);
    assertEquals("3♣", card.toString());
  }

  @Test
  public void toStringHearts() {
    ASlot card = new CardSlot(1, Suit.HEARTS);
    assertEquals("A♥", card.toString());
  }

  @Test
  public void toStringDiamonds() {
    ASlot card = new CardSlot(10, Suit.DIAMONDS);
    assertEquals("10♦", card.toString());
  }

  @Test
  public void toStringSpades() {
    ASlot card = new CardSlot(CardSlot.KING, Suit.SPADES);
    assertEquals("K♠", card.toString());
  }
}
