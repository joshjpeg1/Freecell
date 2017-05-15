package cs3500.hw02.card;

/**
 * Represents a Card in a game of Freecell.
 */
public class Card {
  private int value;
  private Suit suit;

  /**
   * Constructs a {@code Card} object.
   *
   * @param value      the value of a card
   * @param suit       the suit of a card
   * @throws IllegalArgumentException if value is less than 1 or greater than 13
   */
  public Card(int value, Suit suit) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Invalid Card Value");
    }
    this.value = value;
    this.suit = suit;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Card)) {
      return false;
    }
    return this.value == ((Card) that).value
      && this.suit.equals(((Card) that).suit);
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
   * Complies the value and suit of a {@code Card} into a String.
   *
   * @return a card as a String
   */
  @Override
  public String toString() {
    switch(this.value) {
      case 1:
        return "A" + this.suit.toString();
      case 11:
        return "J" + this.suit.toString();
      case 12:
        return "Q" + this.suit.toString();
      case 13:
        return "K" + this.suit.toString();
      default:
        return Integer.toString(this.value) + this.suit.toString();
    }
  }
}
