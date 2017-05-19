package cs3500.hw02;

import cs3500.hw02.slot.CardSlot;
import cs3500.hw02.slot.CardSuit;
import cs3500.hw02.slot.CardValue;
import cs3500.hw02.slot.EmptySlot;
import cs3500.hw02.slot.ISlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the model of a game of Freecell.
 */
public class FreecellModel implements FreecellOperations<ISlot> {
  private List<List<ISlot>> cascades;
  private List<ISlot> opens;
  private List<List<ISlot>> foundations;
  private HashMap<PileType, List> piles;

  /**
   * Constructs a {@code FreecellModel} object.
   */
  public FreecellModel() {
    this.cascades = new ArrayList<>();
    this.opens = new ArrayList<>();
    this.foundations = new ArrayList<>();
    this.piles = new HashMap<>();
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
      throw new IllegalArgumentException("Invalid number of cascade piles. Given: "
          + numCascadePiles);
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Invalid number of open piles. Given: "
          + numOpenPiles);
    }
    if (deck == null || deck.contains(null)
        || deck.size() != (CardValue.values().length * CardSuit.values().length)
        || !Utils.noDuplicates(deck)) {
      throw new IllegalArgumentException("Invalid deck.");
    }

    this.foundations = new ArrayList<>();
    for (int i = 0; i < CardSuit.values().length; i++) {
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
    this.piles = new HashMap<>();
    this.piles.put(PileType.CASCADE, this.cascades);
    this.piles.put(PileType.FOUNDATION, this.foundations);
    this.piles.put(PileType.OPEN, this.opens);

    List<ISlot> deckCopy = new ArrayList<>();
    if (shuffle) {
      deckCopy = Utils.shuffle(deck);
    } else {
      for (ISlot card : deck) {
        deckCopy.add(card);
      }
    }
    while (deckCopy.size() > 0) {
      for (int i = 0; i < this.cascades.size(); i++) {
        if (deckCopy.size() > 0) {
          this.addSafelyPile(this.cascades.get(i), deckCopy.remove(0));
        }
      }
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException {
    if (!isGameOver()) {
      this.moveExceptionChecking(source, pileNumber, cardIndex, destination, destPileNumber);
      ISlot from;
      if (source.equals(PileType.CASCADE) || source.equals(PileType.FOUNDATION)) {
        List<List<ISlot>> pile = this.piles.get(source);
        from = this.removeSafelyPile(pile.get(pileNumber), cardIndex);
      } else {
        from = this.opens.remove(pileNumber);
        this.opens.add(pileNumber, new EmptySlot());
      }
      if (destination.equals(PileType.CASCADE) || destination.equals(PileType.FOUNDATION)) {
        List<List<ISlot>> pile = this.piles.get(destination);
        this.addSafelyPile(pile.get(destPileNumber), from);
      } else {
        this.opens.add(destPileNumber, from);
        this.opens.remove(destPileNumber + 1);
      }
    }
  }

  /**
   * Helper to the move method. Checks for any errors in the given arguments or in the desired moves
   * and, if found, throws an argument, stopping the move method from proceeding.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible
   */
  private void moveExceptionChecking(PileType source, int pileNumber, int cardIndex,
                                     PileType destination, int destPileNumber)
                                     throws IllegalArgumentException {
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Invalid PileType given.");
    }
    ISlot from;
    if (source.equals(PileType.CASCADE) || source.equals(PileType.FOUNDATION)) {
      List<List<ISlot>> pile = this.piles.get(source);
      if (pileNumber < 0 || pileNumber >= pile.size()) {
        throw new IllegalArgumentException("Pile does not exist at given source index.");
      }
      if (cardIndex != pile.get(pileNumber).size() - 1) {
        throw new IllegalArgumentException("Invalid move, must move last card in pile.");
      }
      from = pile.get(pileNumber).get(cardIndex);
    } else if (source.equals(PileType.OPEN)) {
      if (pileNumber < 0 || pileNumber >= this.opens.size()) {
        throw new IllegalArgumentException("Pile does not exist at given source index.");
      }
      if (cardIndex != 0) {
        throw new IllegalArgumentException("Card does not exist at given source index.");
      }
      from = this.opens.get(pileNumber);
    } else {
      throw new IllegalArgumentException("Given pile type does not exist.");
    }
    if (destination.equals(PileType.CASCADE) || destination.equals(PileType.FOUNDATION)) {
      List<List<ISlot>> pile = this.piles.get(destination);
      if (destPileNumber < 0 || destPileNumber >= pile.size()) {
        throw new IllegalArgumentException("Pile does not exist at given destination index.");
      }

      if (!from.moveTo(Utils.getLast(pile.get(destPileNumber)), destination)) {
        throw new IllegalArgumentException("Move is illegal.");
      }
    } else if (destination.equals(PileType.OPEN)) {
      if (destPileNumber < 0 || destPileNumber >= this.opens.size()) {
        throw new IllegalArgumentException("Pile does not exist at given destination index.");
      }
      if (!from.moveTo(this.opens.get(destPileNumber), destination)) {
        throw new IllegalArgumentException("Move is illegal.");
      }
    } else {
      throw new IllegalArgumentException("Given pile type does not exist.");
    }
  }

  /**
   * Helper to the move method. Removes a slot (card or empty) safely from a pile, by adding a new
   * empty in case the list is empty.
   *
   * @param pile           a singular pile
   * @param index          the index to remove the slot from the pile
   * @return the removed {@code ISlot}
   * @throws IllegalArgumentException if given List is or contains null
   */
  private ISlot removeSafelyPile(List<ISlot> pile, int index) throws IllegalArgumentException {
    if (pile == null || pile.contains(null)) {
      throw new IllegalArgumentException("The given pile cannot be used.");
    }
    ISlot slot = pile.remove(index);
    if (pile.size() == 0) {
      pile.add(new EmptySlot());
    }
    return slot;
  }

  /**
   * Helper to the move method. Adds a slot (card or empty) safely to a pile, by removing any
   * empties the given pile had.
   *
   * @param pile           a singular pile
   * @param slot           the {@code ISlot} to add to the pile
   * @throws IllegalArgumentException if given List is/contains null, or if the given ISlot is null
   */
  private void addSafelyPile(List<ISlot> pile, ISlot slot) throws IllegalArgumentException {
    if (pile == null || pile.contains(null)) {
      throw new IllegalArgumentException("The given pile cannot be used.");
    } else if (slot == null) {
      throw new IllegalArgumentException("Must give a non-null ISlot.");
    }
    pile.add(slot);
    if (pile.contains(new EmptySlot())) {
      pile.remove(new EmptySlot());
    }
  }

  @Override
  public boolean isGameOver() {
    if (this.foundations.size() == 0 || this.opens.size() == 0 || this.cascades.size() == 0) {
      return false;
    }
    for (List<ISlot> pile : this.foundations) {
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
