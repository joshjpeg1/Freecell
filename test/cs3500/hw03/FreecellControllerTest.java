package cs3500.hw03;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;
import cs3500.hw02.Utils;
import cs3500.hw02.slot.ISlot;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.hw04.FreecellModelCreator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for {@link FreecellController}.
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameGameStateShuffled() {
    fcc = new FreecellController(new StringReader("q"), buffer);
    fcc.playGame(model.getDeck(), model, 4, 10, false);

    Appendable buffer2 = new StringBuffer();
    IFreecellController fcc2 = new FreecellController(new StringReader("q"), buffer2);
    fcc2.playGame(model.getDeck(), model, 4, 10, true);

    assertNotEquals(buffer2.toString(), buffer.toString());
  }

  @Test
  public void playGameUppercaseLowercaseSameResult() {
    fcc = new FreecellController(new StringReader("C1\n7\nO1\nQ"), buffer);
    fcc.playGame(model.getDeck(), model, 4, 10, false);

    Appendable buffer2 = new StringBuffer();
    IFreecellController fcc2 = new FreecellController(new StringReader("c1\n7\no1\nq"), buffer2);
    fcc2.playGame(model.getDeck(), model, 4, 10, false);

    assertEquals(buffer.toString(), buffer2.toString());
  }

  @Test
  public void playGameSpacesNewLinesSameResult() {
    fcc = new FreecellController(new StringReader("c1\n7\no1\nq"), buffer);
    fcc.playGame(model.getDeck(), model, 4, 10, false);

    Appendable buffer2 = new StringBuffer();
    IFreecellController fcc2 = new FreecellController(new StringReader("c1 7 o1 q"), buffer2);
    fcc2.playGame(model.getDeck(), model, 4, 10, false);

    assertEquals(buffer.toString(), buffer2.toString());
  }

  @Test
  public void playGameNonEmptyAppendable() {
    Appendable app = new StringBuffer("ALREADY WRITTEN\n");
    fcc = new FreecellController(new StringReader("q"), app);
    fcc.playGame(Utils.reverse(model.getDeck()), model, 8, 4, false);
    assertEquals("ALREADY WRITTEN\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", app.toString());
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameSourcePileTooLow() {
    fcc = new FreecellController(new StringReader("c0 7 o1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Pile does not exist at given source index.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameSourcePileTooHigh() {
    fcc = new FreecellController(new StringReader("c9 7 o1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Pile does not exist at given source index.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameSourceCardTooLow() {
    fcc = new FreecellController(new StringReader("c1 5 o1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Invalid move, must move last card in pile.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameSourceCardTooHigh() {
    fcc = new FreecellController(new StringReader("c1 8 o1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Invalid move, must move last card in pile.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameDestPileTooLow() {
    fcc = new FreecellController(new StringReader("c1 7 o0 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Pile does not exist at given destination index.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameDestPileTooHigh() {
    fcc = new FreecellController(new StringReader("c1 7 o5 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Pile does not exist at given destination index.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveOntoSelf() {
    fcc = new FreecellController(new StringReader("c1 7 c1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameCascadeDestHighOnLow() {
    fcc = new FreecellController(new StringReader("c1 7 c5 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenDestFilled() {
    fcc = new FreecellController(new StringReader("c5 6 o1 c6 6 o1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: 2♠\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameFoundationDestEmptyNotAce() {
    fcc = new FreecellController(new StringReader("c5 6 f1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameFoundationDestWrongSuit() {
    fcc = new FreecellController(new StringReader("c1 7 f1 c6 6 f1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameFoundationDestWrongNextValue() {
    fcc = new FreecellController(new StringReader("c1 7 f1 c5 6 o1 c5 5 f1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1: 2♠\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameInvalidPileType() {
    fcc = new FreecellController(new StringReader("e1 c1 7 f1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid source pile type and number (Given: \"e1\"). Try again.\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameInvalidCardIndexKeepsSourcePile() {
    fcc = new FreecellController(new StringReader("c1 f2 7 f2 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid source card index (Given: \"f2\"). Try again.\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameInvalidDestPileKeepsSourcePileCardIndex() {
    fcc = new FreecellController(new StringReader("c1 7 f45e f2 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid destination pile type and number (Given: \"f45e\"). Try again.\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameNegativeSourcePile() {
    fcc = new FreecellController(new StringReader("c-1 7 f2 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Pile does not exist at given source index.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameNegativeCardIndex() {
    fcc = new FreecellController(new StringReader("c1 -7 7 f2 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid source card index (Given: \"-7\"). Try again.\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameNegativeDestPile() {
    fcc = new FreecellController(new StringReader("c1 7 f-2 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Pile does not exist at given destination index.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameQuitInMiddleOfMove() {
    fcc = new FreecellController(new StringReader("e1 c1 q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid source pile type and number (Given: \"e1\"). Try again.\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameQuitMessage() {
    fcc = new FreecellController(new StringReader("q"), buffer);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameCascadeToCascade() {
    reader = new StringReader("C6 6 C1 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameCascadeToOpen() {
    reader = new StringReader("C4 7 o3 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameCascadeToFoundationEmpty() {
    reader = new StringReader("C1 7 f2 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameCascadeToFoundationFilled() {
    reader = new StringReader("C1 7 f4 c5 6 f4 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4: A♠, 2♠\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToCascade() {
    reader = new StringReader("c8 6 O3 o3 1 C2 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToOpen() {
    reader = new StringReader("c8 6 O3 o3 1 o2 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToFoundationEmpty() {
    reader = new StringReader("C1 7 o1 O1 1 F4 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameOpenToFoundationFilled() {
    reader = new StringReader("C1 7 f1 C5 6 F1 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameFoundationToCascade() {
    reader = new StringReader("c3 7 f1 f1 1 C5 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♦\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠, A♦\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameFoundationToOpen() {
    reader = new StringReader("c3 7 f1 f1 1 O1 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♦\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♦\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameFoundationToFoundation() {
    reader = new StringReader("c3 7 f1 f1 1 f2 q");
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♦\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2: A♦\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameWin4Cascade() {
    String commands = "";
    for (int j = 1; j <= 4; j++) {
      for (int i = 1; i <= 13; i++) {
        commands += "c" + j + " " + (14 - i) + " f" + j + " ";
      }
    }
    reader = new StringReader(commands);
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(model.getDeck()), model, 4, 4, false);
    assertTrue(model.isGameOver());
  }

  @Test
  public void playGameWin8Cascade() {
    String commands = "";
    for (int j = 1; j <= 4; j++) {
      for (int i = 1; i <= 7; i++) {
        commands += "c" + j + " " + (8 - i) + " f" + j + "\n";
        if (8 - (i + 1) >= 1) {
          commands += "c" + (j + 4) + " " + (8 - (i + 1)) + " f" + j + "\n";
        }
      }
    }
    reader = new StringReader(commands);
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(model.getDeck()), model, 8, 4, false);
    assertTrue(model.isGameOver());
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2: K♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣, 10♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣, J♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8:\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:\n"
        + "Game over.", buffer.toString());
  }

  @Test
  public void playGameWin52Cascade() {
    String commands = "";
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        commands += "c" + ((i * 4) + j + 1) + " 1 f" + (j + 1) + "\n";
      }
    }
    reader = new StringReader(commands);
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(model.getDeck(), model, 52, 4, false);
    assertTrue(model.isGameOver());
  }

  @Test
  public void playGameModelAlreadyWin() {
    FreecellOperations fcm = new FreecellModel();
    fcm.startGame(Utils.reverse(fcm.getDeck()), 8, 4, false);
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i <= 6; i++) {
        fcm.move(PileType.CASCADE, j, 6 - i, PileType.FOUNDATION, j);
        if (6 - (i + 1) >= 0) {
          fcm.move(PileType.CASCADE, j + 4, 6 - (i + 1), PileType.FOUNDATION, j);
        }
      }
    }
    assertTrue(fcm.isGameOver());
    fcc = new FreecellController(new StringReader("q"), buffer);
    fcc.playGame(fcm.getDeck(), fcm, 52, 4, false);
    assertFalse(fcm.isGameOver());
  }

  /**
   * CHANGELOG:
   * Added tests for FreecellModelCreator and FreecellMultimoveModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void playGameModelCreatorNullModel() {
    fcc.playGame(new FreecellModel().getDeck(), FreecellModelCreator.create(null), 8, 4, false);
  }

  @Test
  public void playGameModelCreatorSingle() {
    reader = new StringReader("c1 7 c6 c6 6 c1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Invalid move, must move last card in pile.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameModelCreatorMultiple() {
    reader = new StringReader("c1 7 c6 c6 6 c1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildToFoundation() {
    reader = new StringReader("c1 7 c6 c6 6 c1 c1 6 f1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Cannot move multiple cards to a pile other than cascade.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildToOpen() {
    reader = new StringReader("c1 7 c6 c6 6 c1 c1 6 o1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Cannot move multiple cards to a pile other than cascade.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildFromFoundation() {
    reader = new StringReader("c1 7 f1 c5 6 f1 f1 0 c1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Invalid move, must move last card in pile.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveInvalidBuild() {
    reader = new StringReader("c1 4 c2 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Chosen cards do not form a valid cascade build and cannot "
        + "be moved altogether at once.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildToWrongSuit() {
    reader = new StringReader("c2 7 f1 c1 7 c6 c6 6 c2 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♥\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♥\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildToWrongValue() {
    reader = new StringReader("c2 7 f1 c1 7 c6 c6 6 c2 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♥\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♥\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Move is illegal.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildExceedsMaxBuildSizeEmptyOpens() {
    reader = new StringReader("c1 7 c6 c6 6 c1 c1 6 c6 c6 5 c1 c1 5 c6 c6 4 c1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 3♠, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 4♥, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 5♠, 4♥, 3♠, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Trying to move 6 cards, but maximum number currently allowed is 5.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildExceedsMaxBuildSizeFilledOpens() {
    reader = new StringReader("c2 7 o1 c3 7 o2 c4 7 o3 c5 6 o4 c1 7 c6 c6 6 c1 q");
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♥\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♥\nO2: A♦\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♥\nO2: A♦\nO3: A♣\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♥\nO2: A♦\nO3: A♣\nO4: 2♠\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♥\nO2: A♦\nO3: A♣\nO4: 2♠\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Trying to move 2 cards, but maximum number currently allowed is 1.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildExceedsMaxBuildSizeEmptyCascades() {
    String commands = "c1 7 f1\nc5 6 f1\nc1 6 f1\nc5 5 f1\nc1 5 f1\nc5 4 f1\nc1 4 f1\n"
        + "c5 3 f1\nc1 3 f1\nc5 2 f1\nc1 2 f1\nc5 1 f1\n" // opens up one empty cascade pile
        + "c2 7 c8\nc8 6 c2\nc2 6 c8\nc8 5 c2\nc2 5 c8\nc8 4 c2\nc2 4 c8\n"
        + "c8 3 c2\nc2 3 c8\nc8 2 c2\nc2 2 c8\n" // exceeds
        + "q";
    reader = new StringReader(commands);
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 3♥, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 8♣, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 9♥, 8♣, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 10♣, 9♥, 8♣, 7♥, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Invalid move. Try again.\n"
        + "REASON: Trying to move 11 cards, but maximum number currently allowed is 10.\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildEmptyCascades() {
    String commands = "c1 7 f1\nc5 6 f1\nc1 6 f1\nc5 5 f1\nc1 5 f1\nc5 4 f1\nc1 4 f1\n"
        + "c5 3 f1\nc1 3 f1\nc5 2 f1\nc1 2 f1\nc5 1 f1\n" // opens up one empty cascade pile
        + "c2 7 c8\nc8 6 c2\nc2 6 c8\nc8 5 c2\nc2 5 c8\nc8 4 c5\n"
        + "q";
    reader = new StringReader(commands);
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 3♥, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5:\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: 6♣, 5♥, 4♣, 3♥, 2♣, A♥\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }

  @Test
  public void playGameMoveBuildBackAndForth() {
    String commands = "c1 7 c6\nc6 6 c1\nc1 6 c6\nc6 5 c1\nc1 5 c6\nq";
    reader = new StringReader(commands);
    fcc = new FreecellController(reader, buffer);
    fcc.playGame(Utils.reverse(new FreecellModel().getDeck()),
        FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE), 8, 4, false);
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
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 3♠, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 4♥, 3♠, 2♥, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 5♠, 4♥, 3♠, 2♥, A♠\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Please enter a new move (e.g.: C1 7 F2).\n"
        + "Game quit prematurely.", buffer.toString());
  }
}
