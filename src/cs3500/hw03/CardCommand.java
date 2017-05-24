package cs3500.hw03;

/**
 * Created by josh_jpeg on 5/24/17.
 */
public class CardCommand implements Command {
  private int cardIndex;

  public CardCommand(int cardIndex) {
    this.cardIndex = cardIndex;
  }

  public int getCardIndex() {
    return this.cardIndex;
  }
}
