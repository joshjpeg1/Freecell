package cs3500.hw02.card;

import org.junit.Test;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class CardTest {

  @Test(expected = IllegalArgumentException.class)
  public void constructExample1() {
    Card card = new Card(0, Suit.CLUBS);
  }
}
