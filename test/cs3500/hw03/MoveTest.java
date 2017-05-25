package cs3500.hw03;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;
import cs3500.hw02.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Tests for {@link Move}.
 */
public class MoveTest {
  FreecellOperations model = new FreecellModel();
  Move move = new Move();

  //Tests for the toString() method
  @Test
  public void toStringInitial() {
    assertEquals("(null, null, null, null, null)", new Move().toString());
  }

  @Test
  public void toStringCascadePile() {
    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    assertEquals("(CASCADE, 0, null, null, null)", m.toString());
  }

  @Test
  public void toStringOpenPile() {
    Move m = new Move();
    m.setPile(PileType.OPEN, 1, true);
    assertEquals("(OPEN, 1, null, null, null)", m.toString());
  }

  @Test
  public void toStringFoundationPile() {
    Move m = new Move();
    m.setPile(PileType.FOUNDATION, 3, true);
    assertEquals("(FOUNDATION, 3, null, null, null)", m.toString());
  }

  @Test
  public void toStringSourcePile() {
    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    assertEquals("(CASCADE, 0, null, null, null)", m.toString());
  }

  @Test
  public void toStringCardIndex() {
    Move m = new Move();
    m.setCardIndex(7);
    assertEquals("(null, null, 7, null, null)", m.toString());
  }

  @Test
  public void toStringDestPile() {
    Move m = new Move();
    m.setPile(PileType.FOUNDATION, 3, false);
    assertEquals("(null, null, null, FOUNDATION, 3)", m.toString());
  }

  @Test
  public void toStringFull() {
    Move m = new Move();
    m.setPile(PileType.OPEN, 2, true);
    m.setCardIndex(1);
    m.setPile(PileType.CASCADE, 4, false);
    assertEquals("(OPEN, 2, 1, CASCADE, 4)", m.toString());
  }

  // Tests for the setPile() method
  @Test(expected = IllegalArgumentException.class)
  public void setPileNullPileType() {
    move.setPile(null, 3, true);
  }

  @Test
  public void setPileSourcePile() {
    move.setPile(PileType.OPEN, 8, true);
    assertEquals("(OPEN, 8, null, null, null)", move.toString());
  }

  @Test
  public void setPileDestPile() {
    move.setPile(PileType.FOUNDATION, 1, false);
    assertEquals("(null, null, null, FOUNDATION, 1)", move.toString());
  }

  @Test
  public void setPileOverwrite() {
    move.setPile(PileType.OPEN, 8, true);
    move.setPile(PileType.FOUNDATION, 3, true);
    assertEquals("(FOUNDATION, 3, null, null, null)", move.toString());
  }

  // Tests for the setCardIndex() method
  @Test
  public void setCardIndexRegular() {
    move.setCardIndex(4);
    assertEquals("(null, null, 4, null, null)", move.toString());
  }

  @Test
  public void setCardIndexNegative() {
    move.setCardIndex(-4);
    assertEquals("(null, null, -4, null, null)", move.toString());
  }

  // Tests for the searchingFor() method
  @Test
  public void searchingForInitial() {
    Move m = new Move();
    assertEquals(SearchState.SOURCE_PILE, m.searchingFor());
  }

  @Test
  public void searchingForStillSourcePileWhenFillCardIndex() {
    Move m = new Move();
    m.setCardIndex(5);
    assertEquals(SearchState.SOURCE_PILE, m.searchingFor());
  }

  @Test
  public void searchingForStillSourcePileWhenFillDestPile() {
    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, false);
    assertEquals(SearchState.SOURCE_PILE, m.searchingFor());
  }

  @Test
  public void searchingForSourcePileToCardIndex() {
    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    assertEquals(SearchState.CARD_INDEX, m.searchingFor());
  }

  @Test
  public void searchingForSourcePileSkipCardIndex() {
    Move m = new Move();
    m.setCardIndex(6);
    m.setPile(PileType.CASCADE, 0, true);
    assertEquals(SearchState.DEST_PILE, m.searchingFor());
  }

  @Test
  public void searchingForFinishedInOrder() {
    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 3, false);
    assertEquals(SearchState.FINISHED, m.searchingFor());
  }

  @Test
  public void searchingForFinishedOutOfOrder() {
    Move m = new Move();
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 3, false);
    m.setPile(PileType.CASCADE, 0, true);
    assertEquals(SearchState.FINISHED, m.searchingFor());
  }

  // Tests for the tryMove() method
  @Test(expected = IllegalArgumentException.class)
  public void tryMoveNullModel() {
    Move m = new Move();
    m.tryMove(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveSourcePileTooLow() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, -1, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveSourcePileTooHigh() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 8, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveSourceCardTooLow() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(5);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveSourceCardTooHigh() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(7);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveDestPileTooLow() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, -1, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveDestPileTooHigh() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, 4, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveOntoSelf() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 4, true);
    m.setCardIndex(5);
    m.setPile(PileType.CASCADE, 4, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveCascadeDestHighOnLow() {
    model.startGame(model.getDeck(), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.CASCADE, 4, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveOpenDestFilled() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 4, true);
    m.setCardIndex(5);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);

    m.setPile(PileType.CASCADE, 5, true);
    m.setCardIndex(5);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveFoundationDestEmptyNotAce() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 4, true);
    m.setCardIndex(5);
    m.setPile(PileType.FOUNDATION, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveFoundationDestWrongSuit() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 0, false);
    m.tryMove(model);

    m.setPile(PileType.CASCADE, 5, true);
    m.setCardIndex(5);
    m.setPile(PileType.FOUNDATION, 0, false);
    m.tryMove(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryMoveFoundationDestWrongNextValue() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 0, false);
    m.tryMove(model);

    m.setPile(PileType.CASCADE, 4, true);
    m.setCardIndex(5);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);

    m.setPile(PileType.CASCADE, 4, true);
    m.setCardIndex(4);
    m.setPile(PileType.FOUNDATION, 2, false);
    m.tryMove(model);
  }

  @Test
  public void tryMoveUnfinishedMove() {
    Move m = new Move();
    model.startGame(model.getDeck(), 8, 4, false);
    assertFalse(m.tryMove(model));
  }

  @Test
  public void tryMoveCascadeToCascade() {
    
    model.startGame(model.getDeck(), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 5, true);
    m.setCardIndex(5);
    m.setPile(PileType.CASCADE, 0, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣, Q♦\n"
        + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C6: 2♦, 4♦, 6♦, 8♦, 10♦\n"
        + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠", model.getGameState());
  }

  @Test
  public void tryMoveCascadeToOpen() {
    model.startGame(model.getDeck(), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 5, true);
    m.setCardIndex(5);
    m.setPile(PileType.OPEN, 1, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2: Q♦\nO3:\nO4:\n"
        + "C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C6: 2♦, 4♦, 6♦, 8♦, 10♦\n"
        + "C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠", model.getGameState());
  }

  @Test
  public void tryMoveCascadeToFoundationEmpty() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 3, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4: A♠\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveCascadeToFoundationFilled() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 0, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 3, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.CASCADE, 4, true);
    m.setCardIndex(5);
    m.setPile(PileType.FOUNDATION, 3, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4: A♠, 2♠\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveOpenToFoundationEmpty() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 3, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, 2, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.OPEN, 2, true);
    m.setCardIndex(0);
    m.setPile(PileType.FOUNDATION, 1, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2: A♣\nF3:\nF4:\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
      + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
      + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
      + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
      + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
      + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
      + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
      + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveOpenToFoundationFilled() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 3, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 1, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.CASCADE, 7, true);
    m.setCardIndex(5);
    m.setPile(PileType.OPEN, 2, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.OPEN, 2, true);
    m.setCardIndex(0);
    m.setPile(PileType.FOUNDATION, 1, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2: A♣, 2♣\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣", model.getGameState());
  }

  @Test
  public void tryMoveOpenToOpen() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 3, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, 0, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.OPEN, 0, true);
    m.setCardIndex(0);
    m.setPile(PileType.OPEN, 1, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2: A♣\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveOpenToCascade() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 3, true);
    m.setCardIndex(6);
    m.setPile(PileType.OPEN, 0, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.OPEN, 0, true);
    m.setCardIndex(0);
    m.setPile(PileType.CASCADE, 5, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥, A♣\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveFoundationToCascade() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 2, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 0, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.FOUNDATION, 0, true);
    m.setCardIndex(0);
    m.setPile(PileType.CASCADE, 4, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠, A♦\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveFoundationToOpen() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 2, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 0, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.FOUNDATION, 0, true);
    m.setCardIndex(0);
    m.setPile(PileType.OPEN, 0, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2:\nF3:\nF4:\n"
        + "O1: A♦\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveFoundationToFoundation() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    Move m = new Move();
    m.setPile(PileType.CASCADE, 2, true);
    m.setCardIndex(6);
    m.setPile(PileType.FOUNDATION, 0, false);
    assertTrue(m.tryMove(model));

    m.setPile(PileType.FOUNDATION, 0, true);
    m.setCardIndex(0);
    m.setPile(PileType.FOUNDATION, 1, false);
    assertTrue(m.tryMove(model));

    assertEquals("F1:\nF2: A♦\nF3:\nF4:\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣", model.getGameState());
  }

  @Test
  public void tryMoveWin4Cascade() {
    model.startGame(Utils.reverse(model.getDeck()), 4, 4, false);

    for (int j = 0; j < 4; j++) {
      Move m = new Move();
      for (int i = 0; i < 13; i++) {
        m.setPile(PileType.CASCADE, j, true);
        m.setCardIndex(12 - i);
        m.setPile(PileType.FOUNDATION, j, false);
        m.tryMove(model);
      }
    }
    assertTrue(model.isGameOver());
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
      + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
      + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
      + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1:\nC2:\nC3:\nC4:", model.getGameState());
  }

  @Test
  public void tryMoveWin8Cascade() {
    model.startGame(Utils.reverse(model.getDeck()), 8, 4, false);

    for (int j = 0; j < 4; j++) {
      Move m = new Move();
      for (int i = 0; i <= 6; i++) {
        m.setPile(PileType.CASCADE, j, true);
        m.setCardIndex(6 - i);
        m.setPile(PileType.FOUNDATION, j, false);
        m.tryMove(model);
        if (6 - (i + 1) >= 0) {
          m.setPile(PileType.CASCADE, j + 4, true);
          m.setCardIndex(6 - (i + 1));
          m.setPile(PileType.FOUNDATION, j, false);
          m.tryMove(model);
        }
      }
    }
    assertTrue(model.isGameOver());
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:", model.getGameState());
  }

  @Test
  public void tryMoveWin52Cascade() {
    model.startGame(model.getDeck(), 52, 4, false);

    Move m = new Move();
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        m.setPile(PileType.CASCADE, (i * 4) + j, true);
        m.setCardIndex(0);
        m.setPile(PileType.FOUNDATION, j, false);
        m.tryMove(model);
      }
    }
    assertTrue(model.isGameOver());
    assertEquals("F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "O1:\nO2:\nO3:\nO4:\n"
        + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:\nC9:\nC10:\n"
        + "C11:\nC12:\nC13:\nC14:\nC15:\nC16:\nC17:\nC18:\nC19:\nC20:\n"
        + "C21:\nC22:\nC23:\nC24:\nC25:\nC26:\nC27:\nC28:\nC29:\nC30:\n"
        + "C31:\nC32:\nC33:\nC34:\nC35:\nC36:\nC37:\nC38:\nC39:\nC40:\n"
        + "C41:\nC42:\nC43:\nC44:\nC45:\nC46:\nC47:\nC48:\nC49:\nC50:\nC51:\nC52:",
        model.getGameState());
  }

  @Test
  public void tryMoveCantAfterGameOver() {
    tryMoveWin8Cascade();
    Move m = new Move();
    m.setPile(PileType.FOUNDATION, 0, true);
    m.setCardIndex(12);
    m.setPile(PileType.OPEN, 0, false);
    m.tryMove(model);
    assertEquals("F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
      + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
      + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
      + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
      + "O1:\nO2:\nO3:\nO4:\n"
      + "C1:\nC2:\nC3:\nC4:\nC5:\nC6:\nC7:\nC8:", model.getGameState());
  }
}
