package cs3500.hw03;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;
import cs3500.hw02.slot.ISlot;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Created by josh_jpeg on 5/23/17.
 */
public class FreecellControllerTest {
  Readable reader = new StringReader("");
  Appendable writer = new StringWriter();
  IFreecellController fcc = new FreecellController(this.reader, this.writer);
  FreecellOperations model = new FreecellModel();

  // Tests for the constructor
  @Test(expected = IllegalStateException.class)
  public void constructorNullRd() {
    new FreecellController(null, new StringWriter());
  }

  @Test(expected = IllegalStateException.class)
  public void constructorNullAp() {
    new FreecellController(new StringReader("hey"), null);
  }

  @Test(expected = IllegalStateException.class)
  public void constructorBothNull() {
    new FreecellController(null, null);
  }

  // Tests for the playGame() method
  @Test(expected = IllegalArgumentException.class)
  public void playGameNullDeck() {
    fcc.playGame(null, new FreecellModel(), 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameNullModel() {
    fcc.playGame(new FreecellModel().getDeck(), null, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameBothNull() {
    fcc.playGame(null, null, 8, 4, false);
  }

  @Test
  public void playGameLowCascadePiles() {
    fcc.playGame(model.getDeck(), model, 3, 4, false);
    assertEquals("The given arguments are invalid. Reason: Invalid number of cascade piles. "
        + "Given: 3", writer.toString());
  }

  @Test
  public void playGameLowOpenPiles() {
    fcc.playGame(model.getDeck(), model, 4, 0, false);
    assertEquals("The given arguments are invalid. Reason: Invalid number of open piles. Given: 0",
        writer.toString());
  }

  @Test
  public void playGameEmptyDeck() {
    fcc.playGame(new ArrayList<ISlot>(), model, 8, 4, false);
    assertEquals("The given arguments are invalid. Reason: Invalid deck.", writer.toString());
  }

  @Test
  public void playGameContainsNullDeck() {
    List<ISlot> deck = model.getDeck();
    deck.remove(0);
    deck.add(null);
    fcc.playGame(deck, model, 8, 4, false);
    assertEquals("The given arguments are invalid. Reason: Invalid deck.", writer.toString());
  }

  @Test
  public void playGameNotSize52Deck() {
    List<ISlot> deck = model.getDeck();
    deck.remove(0);
    fcc.playGame(deck, model, 8, 4, false);
    assertEquals("The given arguments are invalid. Reason: Invalid deck.", writer.toString());
  }

  @Test
  public void playGameDuplicatesDeck() {
    List<ISlot> deck = model.getDeck();
    deck.remove(0);
    deck.add(deck.get(0));
    fcc.playGame(deck, model, 8, 4, false);
    assertEquals("The given arguments are invalid. Reason: Invalid deck.", writer.toString());
  }

  @Test
  public void playGameGameStateRegular() {
    fcc.playGame(model.getDeck(), model, 8, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠", writer.toString());
  }

  @Test
  public void playGameGameStateLowCascHighOpen() {
    fcc.playGame(model.getDeck(), model, 4, 10, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\nO5:\nO6:\nO7:\nO8:\nO9:\nO10:\n"
      + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
      + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
      + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
      + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", writer.toString());
  }
}
