package cs3500.hw03;

import cs3500.hw02.FreecellOperations;
import cs3500.hw02.PileType;

/**
 * Represents a move in the game of Freecell. Contains all data needed to perform a move, as
 * dictated by the {@link FreecellOperations} interface definition of move().
 */
public class Move {
  private PileType source;
  private Integer pileNumber;
  private Integer cardIndex;
  private PileType destination;
  private Integer destPileNumber;

  /**
   * Constructs a {@code Move} object, with all fields set to null.
   */
  public Move() {
    this.source = null;
    this.pileNumber = null;
    this.cardIndex = null;
    this.destination = null;
    this.destPileNumber = null;
  }

  /**
   * Sets a pile (determined by the {@code setSource} boolean) to the given pile type and number.
   *
   * @param pileType      the type of pile
   * @param pileNumber    the number of the pile
   * @param setSource     true if setting the source pile, otherwise destination pile
   * @throws IllegalArgumentException if the given pileType is null
   */
  public void setPile(PileType pileType, int pileNumber, boolean setSource)
                      throws IllegalArgumentException{
    if (pileType == null) {
      throw new IllegalArgumentException("Cannot assign null to pile type.");
    }
    if (setSource) {
      this.source = pileType;
      this.pileNumber = pileNumber;
    } else {
      this.destination = pileType;
      this.destPileNumber = pileNumber;
    }
  }

  /**
   * Sets the card index to the given one.
   *
   * @param cardIndex     the index of the card
   */
  public void setCardIndex(int cardIndex) {
    this.cardIndex = cardIndex;
  }

  /**
   * Returns what the {@code Move} object is looking to get next from the input.
   *
   * @return the {@code SearchState} of this, or null if the move is complete
   */
  public SearchState searchingFor() {
    if (this.source == null) {
      return SearchState.SOURCE_PILE;
    } else if (this.cardIndex == null) {
      return SearchState.CARD_INDEX;
    } else if (this.destination == null) {
      return SearchState.DEST_PILE;
    } else {
      return SearchState.FINISHED;
    }
  }

  /**
   * Uses the move method from the given {@link FreecellOperations} object and fills in the
   * arguments with this object's fields.
   *
   * @param model     the model of the current game
   * @throws IllegalArgumentException if the given model is uninitialized
   */
  public void tryMove(FreecellOperations model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot move a null.");
    }
    model.move(this.source, this.pileNumber, this.cardIndex, this.destination, this.destPileNumber);
  }
}
