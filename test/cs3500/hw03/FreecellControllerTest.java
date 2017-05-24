package cs3500.hw03;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;
import cs3500.hw02.Utils;
import cs3500.hw02.slot.ISlot;

import java.io.StringReader;
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
  Appendable buffer = new StringBuffer();
  IFreecellController fcc = new FreecellController(this.reader, this.buffer);
  FreecellOperations model = new FreecellModel();

  // Tests for the playGame() method
  @Test(expected = IllegalStateException.class)
  public void playGameNullRd() {
    IFreecellController fcc2 = new FreecellController(null, new StringBuffer());
    fcc2.playGame(model.getDeck(), model, 8, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void playGameNullAp() {
    IFreecellController fcc2 = new FreecellController(new StringReader("hey"), null);
    fcc2.playGame(model.getDeck(), model, 8, 4, false);
  }

  @Test(expected = IllegalStateException.class)
  public void playGameBothNullRdAp() {
    IFreecellController fcc2 = new FreecellController(null, null);
    fcc2.playGame(model.getDeck(), model, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameNullDeck() {
    fcc.playGame(null, new FreecellModel(), 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameNullModel() {
    fcc.playGame(new FreecellModel().getDeck(), null, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameBothNullDeckModel() {
    fcc.playGame(null, null, 8, 4, false);
  }

  @Test
  public void playGameLowCascadePiles() {
    fcc.playGame(model.getDeck(), model, 3, 4, false);
    assertEquals("Could not start game.", buffer.toString());
  }

  @Test
  public void playGameLowOpenPiles() {
    fcc.playGame(model.getDeck(), model, 4, 0, false);
    assertEquals("Could not start game.", buffer.toString());
  }

  @Test
  public void playGameEmptyDeck() {
    fcc.playGame(new ArrayList<ISlot>(), model, 8, 4, false);
    assertEquals("Could not start game.", buffer.toString());
  }

  @Test
  public void playGameContainsNullDeck() {
    List<ISlot> deck = model.getDeck();
    deck.remove(0);
    deck.add(null);
    fcc.playGame(deck, model, 8, 4, false);
    assertEquals("Could not start game.", buffer.toString());
  }

  @Test
  public void playGameNotSize52Deck() {
    List<ISlot> deck = model.getDeck();
    deck.remove(0);
    fcc.playGame(deck, model, 8, 4, false);
    assertEquals("Could not start game.", buffer.toString());
  }

  @Test
  public void playGameDuplicatesDeck() {
    List<ISlot> deck = model.getDeck();
    deck.remove(0);
    deck.add(deck.get(0));
    fcc.playGame(deck, model, 8, 4, false);
    assertEquals("Could not start game.", buffer.toString());
  }

  @Test
  public void playGameGameStateRegular() {
    fcc = new FreecellController(new StringReader("q"), buffer);
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
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameGameStateLowCascHighOpen() {
    fcc = new FreecellController(new StringReader("q"), buffer);
    fcc.playGame(model.getDeck(), model, 4, 10, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\nO5:\nO6:\nO7:\nO8:\nO9:\nO10:\n"
      + "C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
      + "C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
      + "C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
      + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
      + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameUppercaseQSameResult() {
    fcc = new FreecellController(new StringReader("q"), buffer);
    fcc.playGame(model.getDeck(), model, 4, 10, false);

    Appendable buffer2 = new StringBuffer();
    IFreecellController fcc2 = new FreecellController(new StringReader("Q"), buffer2);
    fcc2.playGame(model.getDeck(), model, 4, 10, false);

    assertEquals(buffer.toString(), buffer2.toString());
  }

  @Test
  public void playGameNoInput() {
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(model.getDeck(), model, 8, 4, false);
    assertEquals("", buffer.toString());
  }

  // null input? StringReader throws a NullPointerException when given null

  @Test
  public void playGameCascadeToCascadeLegal() {
    //reader = new StringReader("e1 23 f30 c1");
    reader = new StringReader("C5 5 C0 q");
    fcc = new FreecellController(reader, buffer);
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
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣, Q♦\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "Game quit prematurely.", buffer.toString());
  }

  /*@Test
  public void playGameCascadeToCascadeIllegal() {
    //reader = new StringReader("e1 23 f30 c1");
    reader = new StringReader("C5 4 C0 q");
    fcc = new FreecellController(reader, buffer);
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
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "Invalid move. Try again.", buffer.toString());
  }*/

  @Test
  public void playGameCascadeToFoundationLegal() {
    reader = new StringReader("C0 6 f1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(model.getDeck()), model, 8, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
      + "F1:\nF2: A♠\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
      + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameCascadeToOpenLegal() {
    reader = new StringReader("C3 6 o2 q");
    fcc = new FreecellController(reader, buffer);
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
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3: K♠\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToCascadeLegal() {
    reader = new StringReader("c7 5 O2 o2 0 C1 q");
    fcc = new FreecellController(reader, buffer);
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
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3: Q♠\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦, Q♠\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠\n"
      + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToOpenLegal() {
    reader = new StringReader("c7 5 O2 o2 0 o1 q");
    fcc = new FreecellController(reader, buffer);
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
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3: Q♠\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2: Q♠\nO3:\nO4:\n"
      + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
      + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
      + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
      + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
      + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
      + "C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
      + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
      + "C8: 2♠, 4♠, 6♠, 8♠, 10♠\n"
      + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToFoundationLegal() {
    reader = new StringReader("C0 6 o0 O0 0 F3 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(model.getDeck()), model, 8, 4, false);
    assertEquals("F1:\nF2:\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
      + "F1:\nF2:\nF3:\nF4:\n"
      + "O1: A♠\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
      + "F1:\nF2:\nF3:\nF4: A♠\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
      + "Game quit prematurely.", buffer.toString());
  }

  // test if game is over
  // test when given model has already been played
  // test when given model is already in progress
}
