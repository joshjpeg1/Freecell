package cs3500.hw02.card;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests for {@link Card}s. */
public class CardTest {

  @Test(expected = IllegalArgumentException.class)
  public void constructExample1() {
    Card card = new Card(0, Suit.CLUBS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructExample2() {
    Card card = new Card(14, Suit.CLUBS);
  }

  @Test
  public void toStringExample1() {
    Card card = new Card(1, Suit.CLUBS);
    assertEquals("A♣", card.toString());
  }

  @Test
  public void toStringExample2() {
    Card card = new Card(2, Suit.CLUBS);
    assertEquals("2♣", card.toString());
  }

  @Test
  public void toStringExample3() {
    Card card = new Card(10, Suit.CLUBS);
    assertEquals("10♣", card.toString());
  }

  @Test
  public void toStringExample4() {
    Card card = new Card(11, Suit.CLUBS);
    assertEquals("J♣", card.toString());
  }

  @Test
  public void toStringExample5() {
    Card card = new Card(12, Suit.CLUBS);
    assertEquals("Q♣", card.toString());
  }

  @Test
  public void toStringExample6() {
    Card card = new Card(13, Suit.CLUBS);
    assertEquals("K♣", card.toString());
  }

  @Test
  public void toStringExample7() {
    Card card = new Card(1, Suit.HEARTS);
    assertEquals("A♥", card.toString());
  }

  @Test
  public void toStringExample8() {
    Card card = new Card(1, Suit.DIAMONDS);
    assertEquals("A♦", card.toString());
  }

  @Test
  public void toStringExample9() {
    Card card = new Card(1, Suit.SPADES);
    assertEquals("A♠", card.toString());
  }
}
