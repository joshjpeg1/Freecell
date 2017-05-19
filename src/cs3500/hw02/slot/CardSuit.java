package cs3500.hw02.slot;

/**
 * Represents all variations of suits of a CardSlot in a game of Freecell.
 */
public enum CardSuit {
  CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

  private final String value;

  /**
   * Constructs a {@code CardSuit} object.
   *
   * @param value      the value of a suit
   */
  CardSuit(String value) {
    this.value = value;
  }

  /**
   * Returns a {@code CardSuit} as a String.
   *
   * @return the suit as a String
   */
  @Override
  public String toString() {
    return value;
  }

  /**
   * Returns whether this and the given suit are opposite suit colors.
   *
   * @param other     the suit being compared against
   * @return whether the two suit are opposite colors
   * @throws IllegalArgumentException if given CardSuit is null
   */
  protected boolean oppositeColor(CardSuit other) throws IllegalArgumentException {
    if (other == null) {
      throw new IllegalArgumentException("Cannot check opposite color against null.");
    }
    if (this.equals(DIAMONDS) || this.equals(HEARTS)) {
      return other.equals(CLUBS) || other.equals(SPADES);
    } else {
      return other.equals(DIAMONDS) || other.equals(HEARTS);
    }
  }
}
