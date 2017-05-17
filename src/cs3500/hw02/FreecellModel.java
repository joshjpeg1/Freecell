package cs3500.hw02;

import cs3500.hw02.slot.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Reprsents the model of a game of Freecell.
 */
public class FreecellModel implements FreecellOperations<ASlot> {
  private Random rand;
  public List<List<ASlot>> cascades;
  private List<ASlot> opens;
  private List<List<ASlot>> foundations;

  /**
   * Constructs a {@code FreecellModel} object.
   */
  public FreecellModel() {
    this.rand = new Random();
    this.foundations = new ArrayList<>(4);
    this.startGame(this.getDeck(), 8, 4, true);
  }

  /**
   * Constructs a {@code FreecellModel} object, with a defined number of cascade piles, open piles,
   * and whether the deck is shuffled from the beginning.
   *
   * @param numCascadePiles  number of cascade piles, ranging from 4 to 8
   * @param numOpenPiles     number of open piles, ranging from 1 to 4
   * @param shuffle          if true, shuffle the deck else deal the deck as-is
   */
  public FreecellModel(int numCascadePiles, int numOpenPiles, boolean shuffle) {
    rand = new Random();
    foundations = new ArrayList<>(4);
    this.startGame(this.getDeck(), numCascadePiles, numOpenPiles, shuffle);
  }

  @Override
  public List<ASlot> getDeck() {
    List<ASlot> deck = new ArrayList<>();
    for (int i = 1; i <= 13; i += 1) {
      for (Suit suit : Suit.values()) {
        deck.add(new CardSlot(i, suit));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<ASlot> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) throws IllegalArgumentException {
    if (numCascadePiles < 4 || numCascadePiles > 8) {
      throw new IllegalArgumentException("Invalid number of cascade piles.");
    }
    if (numOpenPiles < 1 || numOpenPiles > 4) {
      throw new IllegalArgumentException("Invalid number of open piles.");
    }
    if (deck.size() != 52 || !Utils.noDuplicates(deck)) {
      throw new IllegalArgumentException("Invalid deck.");
    }

    this.foundations = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      this.foundations.add(new ArrayList<>(Arrays.asList(new EmptySlot())));
    }
    this.cascades = new ArrayList<>();
    for (int i = 0; i < numCascadePiles; i++) {
      this.cascades.add(new ArrayList<>(Arrays.asList(new EmptySlot())));
    }
    this.opens = new ArrayList<>();
    for (int i = 0; i < numOpenPiles; i++) {
      this.opens.add(new EmptySlot());
    }

    if (shuffle) {
      deck = Utils.shuffle(deck);
    }

    while (deck.size() > 0) {
      for (int i = 0; i < this.cascades.size(); i++) {
        if (deck.size() > 0) {
          if (this.cascades.contains(new EmptySlot())) {
            this.cascades.remove(new EmptySlot());
          }
          this.cascades.get(i).add(deck.remove(0));
        }
      }
    }
  }

  public List getPile(PileType type) {
    switch (type) {
      case CASCADE:
        return cascades;
      case FOUNDATION:
        return foundations;
      case OPEN:
        return opens;
      default:
        return new ArrayList();
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IndexOutOfBoundsException {
    ASlot from;
    if (source.equals(PileType.CASCADE) || source.equals(PileType.FOUNDATION)) {
      List<List<ASlot>> pile = getPile(source);
      if (pileNumber < 0 || pileNumber >= pile.size()) {
        throw new IndexOutOfBoundsException(source.toString() + " pile does not exist at " +
          "given source index.");
      }
      if (cardIndex < 0 || cardIndex >= pile.get(pileNumber).size()) {
        throw new IndexOutOfBoundsException("Card does not exist at given source index.");
      }
      from = pile.get(pileNumber).get(cardIndex);
    } else {
      if (pileNumber < 0 || pileNumber >= opens.size()) {
        throw new IndexOutOfBoundsException("Open pile does not exist at " +
          "given source index.");
      }
      if (cardIndex != 0) {
        throw new IndexOutOfBoundsException("Card does not exist at given source index.");
      }
      from = opens.get(pileNumber);
    }

    if (destination.equals(PileType.CASCADE) || destination.equals(PileType.FOUNDATION)) {
      List<List<ASlot>> pile = getPile(destination);
      if (destPileNumber < 0 || destPileNumber >= pile.size()) {
        throw new IndexOutOfBoundsException(destination.toString() + " pile does not exist at " +
          "given destination index.");
      }
      if (!from.moveTo(Utils.getLast(pile.get(destPileNumber)), destination)) {
        throw new IllegalArgumentException("Move is illegal.");
      }
    } else {
      if (destPileNumber < 0 || destPileNumber >= opens.size()) {
        throw new IndexOutOfBoundsException("Open pile does not exist at " +
          "given destination index.");
      }
      if (!from.moveTo(opens.get(destPileNumber), PileType.OPEN)) {
        throw new IllegalArgumentException("Move is illegal.");
      }
    }

  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String getGameState() {
    String str = "";
    for (int i = 0; i < this.foundations.size(); i++) {
      str += "F" + (i + 1) + ":" + Utils.listToString(this.foundations.get(i)) + "\n";
    }
    for (int i = 0; i < this.opens.size(); i++) {
      str += "O" + (i + 1) + ":" + this.opens.get(i).toString() + "\n";
    }
    for (int i = 0; i < this.cascades.size(); i++) {
      str += "C" + (i + 1) + ":" + Utils.listToString(this.cascades.get(i));
      if (i < this.cascades.size() - 1) {
        str += "\n";
      }
    }
    return str;
  }
}
