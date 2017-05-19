package cs3500.hw02;

import cs3500.hw02.slot.CardSlot;
import cs3500.hw02.slot.CardSuit;
import cs3500.hw02.slot.CardValue;
import cs3500.hw02.slot.EmptySlot;
import cs3500.hw02.slot.ISlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the model of a game of Freecell.
 */
public class FreecellModel implements FreecellOperations<ISlot> {
  private List<List<ISlot>> cascades;
  private List<ISlot> opens;
  private List<List<ISlot>> foundations;

  /**
   * Constructs a {@code FreecellModel} object.
   */
  public FreecellModel() {
    // nothing is initially set in the constructor, but rather in the startGame() method
  }

  @Override
  public List<ISlot> getDeck() {
    List<ISlot> deck = new ArrayList<>();
    for (CardValue val : CardValue.values()) {
      for (CardSuit suit : CardSuit.values()) {
        deck.add(new CardSlot(val, suit));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<ISlot> deck, int numCascadePiles, int numOpenPiles,
                        boolean shuffle) throws IllegalArgumentException {
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Invalid number of cascade piles.");
    }
    if (numOpenPiles < 1 || numOpenPiles > 4) {
      throw new IllegalArgumentException("Invalid number of open piles.");
    }
    if (deck == null || deck.size() != 52 || !Utils.noDuplicates(deck)) {
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
          if (this.cascades.get(i).contains(new EmptySlot())) {
            this.cascades.get(i).remove(new EmptySlot());
          }
          this.cascades.get(i).add(deck.remove(0));
        }
      }
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IndexOutOfBoundsException {
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Invalid PileType given.");
    }
    ISlot from;
    if (source.equals(PileType.CASCADE) || source.equals(PileType.FOUNDATION)) {
      List<List<ISlot>> pile = getPile(source);
      if (pileNumber < 0 || pileNumber >= pile.size()) {
        throw new IndexOutOfBoundsException(source.toString() + " pile does not exist at "
          + "given source index.");
      }
      if (cardIndex != pile.get(pileNumber).size() - 1) {
        throw new IndexOutOfBoundsException("Card does not exist at given source index.");
      }
      from = pile.get(pileNumber).get(cardIndex);
    } else {
      if (pileNumber < 0 || pileNumber >= opens.size()) {
        throw new IndexOutOfBoundsException("Open pile does not exist at "
          + "given source index.");
      }
      if (cardIndex != 0) {
        throw new IndexOutOfBoundsException("Card does not exist at given source index.");
      }
      from = opens.get(pileNumber);
    }

    if (destination.equals(PileType.CASCADE) || destination.equals(PileType.FOUNDATION)) {
      List<List<ISlot>> pile = getPile(destination);
      if (destPileNumber < 0 || destPileNumber >= pile.size()) {
        throw new IndexOutOfBoundsException(destination.toString() + " pile does not exist at "
          + "given destination index.");
      }
      if (!from.moveTo(Utils.getLast(pile.get(destPileNumber)), destination)) {
        throw new IllegalArgumentException("Move is illegal.");
      }
    } else {
      if (destPileNumber < 0 || destPileNumber >= opens.size()) {
        throw new IndexOutOfBoundsException("Open pile does not exist at "
          + "given destination index.");
      }
      if (!from.moveTo(opens.get(destPileNumber), PileType.OPEN)) {
        throw new IllegalArgumentException("Move is illegal.");
      }
    }

    switch (source) {
      case CASCADE:
        this.removeSafelyPile(cascades.get(pileNumber), cascades.get(pileNumber).indexOf(from));
        break;
      case FOUNDATION:
        removeSafelyPile(foundations.get(pileNumber), foundations.get(pileNumber).indexOf(from));
        break;
      case OPEN:
        opens.remove(pileNumber);
        opens.add(pileNumber, new EmptySlot());
        break;
      default:
        throw new IllegalArgumentException("Source PileType unknown.");
    }

    switch (destination) {
      case CASCADE:
        addSafelyPile(cascades.get(destPileNumber), from);
        break;
      case FOUNDATION:
        addSafelyPile(foundations.get(destPileNumber), from);
        break;
      case OPEN:
        opens.add(destPileNumber, from);
        opens.remove(destPileNumber + 1);
        break;
      default:
        throw new IllegalArgumentException("Source PileType unknown.");
    }
  }

  private List getPile(PileType type) {
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

  private void removeSafelyPile(List<ISlot> pile, int index) {
    if (Utils.filterList(pile, new ArrayList<>(Arrays.asList(new EmptySlot()))).size() == 1
        && index == 0) {
      pile.remove(0);
      pile.add(new EmptySlot());
    } else {
      pile.remove(index);
    }
  }

  private void addSafelyPile(List<ISlot> pile, ISlot slot) {
    pile.add(slot);
    if (pile.contains(new EmptySlot())) {
      pile.remove(new EmptySlot());
    }
  }

  @Override
  public boolean isGameOver() {
    for (List<ISlot> pile : foundations) {
      if (pile.size() != CardValue.values().length || !Utils.noDuplicates(pile)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getGameState() {
    String str = "";
    for (int i = 0; i < this.foundations.size(); i++) {
      str += "F" + (i + 1) + ":" +  Utils.listToString(Utils.filterList(this.foundations.get(i),
          new ArrayList<>(Arrays.asList(new EmptySlot())))) + "\n";
    }
    for (int i = 0; i < this.opens.size(); i++) {
      String card = this.opens.get(i).toString();
      if (!card.equals("")) {
        card = " " + card;
      }
      str += "O" + (i + 1) + ":" + card + "\n";
    }
    for (int i = 0; i < this.cascades.size(); i++) {
      str += "C" + (i + 1) + ":" + Utils.listToString(Utils.filterList(this.cascades.get(i),
          new ArrayList<>(Arrays.asList(new EmptySlot()))));
      if (i < this.cascades.size() - 1) {
        str += "\n";
      }
    }
    return str;
  }
}
