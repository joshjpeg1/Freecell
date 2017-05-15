package cs3500.hw02.card;

/**
 * Represents all variations of Suits of a Card in a game of Freecell.
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
}
