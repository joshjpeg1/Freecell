package cs3500.hw02.slot;

/**
 * Represents all variations of values of a CardSlot in a game of Freecell.
 */
public enum CardValue {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
  EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

  protected final int id;

  /**
   * Constructs a {@code CardValue} object.
   *
   * @param id      the id of a card
   */
  CardValue(int id) {
    this.id = id;
  }

  /**
   * Returns a {@code CardValue} as a String.
   *
   * @return the face value of a card
   */
  @Override
  public String toString() {
    switch (this) {
      case ACE:
        return "A";
      case JACK:
        return "J";
      case QUEEN:
        return "Q";
      case KING:
        return "K";
      default:
        return Integer.toString(this.id);
    }
  }

  /**
   * Returns the difference of this and the given {@code CardValue}.
   *
   * @return the difference of two values' ids
   */
  protected int getDifference(CardValue other) {
    return this.id - other.id;
  }
}
