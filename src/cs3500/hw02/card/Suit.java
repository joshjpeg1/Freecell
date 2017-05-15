package cs3500.hw02.card;

/**
 * Created by josh_jpeg on 5/14/17.
 */
public enum Suit {
  CLUBS("Clubs"), DIAMONDS("Diamonds"), HEARTS("Hearts"), SPADES("Spades");

  String value;
  Suit(String value) {
    this.value = value;
  }
}
