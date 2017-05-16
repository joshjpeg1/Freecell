package cs3500.hw02.slot;

import cs3500.hw02.PileType;

/**
 * Represents a card slot in a game of Freecell.
 */
public class CardSlot extends ASlot {
  public static final int ACE = 1;
  public static final int JACK = 11;
  public static final int QUEEN = 12;
  public static final int KING = 13;

  public int value;
  private Suit suit;

  /**
   * Constructs a {@code CardSlot} object.
   *
   * @param value      the value of a slot
   * @param suit       the suit of a slot
   * @throws IllegalArgumentException if value is less than 1 or greater than 13
   */
  public CardSlot(int value, Suit suit) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Invalid CardSlot Value");
    }
    this.value = value;
    this.suit = suit;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof CardSlot)) {
      return false;
    }
    return this.value == ((CardSlot) that).value
      && this.suit.equals(((CardSlot) that).suit);
  }

  @Override
  public int hashCode() {
    switch (this.suit) {
      case CLUBS:
        return (this.value * 10) + 1;
      case DIAMONDS:
        return (this.value * 10) + 2;
      case SPADES:
        return (this.value * 10) + 3;
      default:
        return (this.value * 10) + 4;
    }
  }

  /**
   * Complies the value and suit of a {@code CardSlot} into a String.
   *
   * @return a card slot as a String
   */
  @Override
  public String toString() {
    switch(this.value) {
      case ACE:
        return "A" + this.suit.toString();
      case JACK:
        return "J" + this.suit.toString();
      case QUEEN:
        return "Q" + this.suit.toString();
      case KING:
        return "K" + this.suit.toString();
      default:
        return Integer.toString(this.value) + this.suit.toString();
    }
  }

  /**
   * Returns whether moving the given {@code CardSlot} onto this {@code CardSlot} in the given
   * pile is possible.
   *
   * @param from       the card moving on this card
   * @param where      the pile this is located
   * @return whether the given card can be moved on this card in the given pile
   */
  @Override
  protected boolean moveTo(CardSlot from, PileType where) {
    switch (where) {
      case CASCADE:
        return this.value - 1 == from.value
          && this.oppositeSuit(from);
      case FOUNDATION:
        return this.value + 1 == from.value
          && this.suit.equals(from.suit);
      default:
        return false;
    }
  }

  /**
   * Returns whether this and the given card have opposite suit colors.
   *
   * @param other       the card being compared against
   * @return whether the two cards are opposite colors
   */
  private boolean oppositeSuit(CardSlot other) {
    return this.suit.oppositeSuit(other.suit);
  }
}
