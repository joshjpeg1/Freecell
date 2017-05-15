package cs3500.hw02.card;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public class Card {
  int value;
  Suit suit;

  public Card(int value, Suit suit) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Invalid Card Value");
    }
    this.value = value;
    this.suit = suit;
  }

  @Override
  public String toString() {
    String finalString;
    switch(this.value) {
      case 1:
        finalString = "Ace";
        break;
      case 11:
        finalString = "Jack";
        break;
      case 12:
        finalString = "Queen";
        break;
      case 13:
        finalString = "King";
        break;
      default:
        finalString = Integer.toString(this.value);
    }
    return finalString + " of " + this.suit.value;
  }
}
