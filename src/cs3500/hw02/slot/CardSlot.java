package cs3500.hw02.slot;

import cs3500.hw02.PileType;

/**
 * Represents a card slot in a game of Freecell.
 */
public class CardSlot implements ISlot {
  public final CardValue value;
  private final CardSuit suit;

  /**
   * Constructs a {@code CardSlot} object.
   *
   * @param value      the value of a slot
   * @param suit       the suit of a slot
   */
  public CardSlot(CardValue value, CardSuit suit) {
    if (value == null || suit == null) {
      throw new IllegalArgumentException("Cannot give null as argument.");
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
    return this.value.equals(((CardSlot) that).value)
      && this.suit.equals(((CardSlot) that).suit);
  }

  @Override
  public int hashCode() {
    switch (this.suit) {
      case CLUBS:
        return (this.value.id * 10) + 1;
      case DIAMONDS:
        return (this.value.id * 10) + 2;
      case SPADES:
        return (this.value.id * 10) + 3;
      case HEARTS:
        return (this.value.id * 10) + 4;
      default:
        return this.value.id * 10;
    }
  }

  /**
   * Complies the value and suit of a {@code CardSlot} into a String.
   *
   * @return a card slot as a String
   */
  @Override
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  /**
   * Returns whether moving this {@code CardSlot} onto the given {@code ISlot} in the given
   * pile is possible.
   *
   * @param to         the slot to be move on
   * @param where      the pile the desired slot is located
   * @return whether this card can move to the other in the given pile
   */
  @Override
  public boolean moveTo(ISlot to, PileType where) {
    return to.moveFrom(this, where);
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
  public boolean moveFrom(CardSlot from, PileType where) {
    switch (where) {
      case CASCADE:
        return this.value.getDifference(from.value) == 1
          && this.oppositeColor(from);
      case FOUNDATION:
        return from.value.getDifference(this.value) == 1
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
  public boolean oppositeColor(CardSlot other) {
    return this.suit.oppositeColor(other.suit);
  }
}
