package cs3500.hw02.slot;

/**
 * Represents all variations of Suits of a CardSlot in a game of Freecell.
 */
public enum Suit {
  CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

  private String value;

  /**
   * Constructs a {@code Suit} object.
   *
   * @param value      the value of a suit
   */
  Suit(String value) {
    this.value = value;
  }

  /**
   * Returns a {@code Suit} as a String.
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
   */
  protected boolean oppositeSuit(Suit other) {
    if (this.equals(DIAMONDS) || this.equals(HEARTS)) {
      return other.equals(CLUBS) || other.equals(SPADES);
    } else {
      return other.equals(DIAMONDS) || other.equals(HEARTS);
    }
  }
}
