package cs3500.hw04;

import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;
import cs3500.hw02.slot.EmptySlot;
import cs3500.hw02.slot.ISlot;

import java.util.List;
import java.util.Stack;

/**
 * Represents the model of a game of Freecell that allows for moving multiple
 * cards at the same time.
 */
public class FreecellMultimoveModel extends FreecellModel {
  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException {
    if (!isGameOver()) {
      this.moveExceptionChecking(source, pileNumber, cardIndex, destination, destPileNumber);
      Stack<ISlot> build = new Stack<>();
      List<ISlot> cascadePile = this.cascades.get(pileNumber);
      if (source.equals(PileType.CASCADE)) {
        while (cascadePile.size() > cardIndex && !cascadePile.contains(new EmptySlot())) {
          //build.push(cascadePile.remove(cascadePile.size() - 1));
          build.push(this.removeSafelyPile(cascadePile, cascadePile.size() - 1));
        }
      } else if (source.equals(PileType.FOUNDATION)) {
        build.push(this.removeSafelyPile(this.foundations.get(pileNumber), cardIndex));
      } else {
        build.push(this.opens.remove(pileNumber));
        this.opens.add(pileNumber, new EmptySlot());
      }
      if (destination.equals(PileType.CASCADE) || destination.equals(PileType.FOUNDATION)) {
        List<List<ISlot>> pile = this.piles.get(destination);
        while (build.size() > 0) {
          this.addSafelyPile(pile.get(destPileNumber), build.pop());
        }
      } else {
        this.opens.add(destPileNumber, build.pop());
        this.opens.remove(destPileNumber + 1);
      }
    }
  }

  @Override
  protected void validateMoveFromCascade(int pileNumber, int cardIndex,
                                         PileType destination) throws IllegalArgumentException {
    List<ISlot> chosenPile = this.cascades.get(pileNumber);
    if (cardIndex < 0 || cardIndex >= chosenPile.size()) {
      throw new IllegalArgumentException("Card does not exist as given index in cascade pile "
          + pileNumber + ".");
    } else if (cardIndex == chosenPile.size() - 1) {
      return;
    } else if (!destination.equals(PileType.CASCADE)) {
      throw new IllegalArgumentException("Cannot move multiple cards to a pile other than "
        + "cascade.");
    }

    Stack<ISlot> build = new Stack<>();
    for (int i = cardIndex; i < chosenPile.size(); i++) {
      build.push(chosenPile.get(i));
    }
    int maxBuildSize = this.getMaxBuildSize();
    if (build.size() > maxBuildSize) {
      throw new IllegalArgumentException("Trying to move " + build.size() + " cards, but maximum "
          + "number currently allowed is " + maxBuildSize + ".");
    }
    while(build.size() > 1) {
      ISlot top = build.pop();
      if (!top.moveTo(build.peek(), destination)) {
        throw new IllegalArgumentException("Chosen cards do not form a valid cascade build and " +
          "cannot be moved altogether at once.");
      }
    }
  }

  /**
   * Calculates the maximum number of cards that can be moved at once.
   *
   * @return the max number of cards that can be moved at once
   */
  private int getMaxBuildSize() {
    int freeOpens = 0;
    for (ISlot slot : this.opens) {
      if (slot.equals(new EmptySlot())) {
        freeOpens += 1;
      }
    }
    int emptyCascades = 0;
    for (List<ISlot> pile : this.cascades) {
      if (pile.contains(new EmptySlot())) {
        emptyCascades += 1;
      }
    }
    return (freeOpens + 1) * ((int) (Math.pow(2, emptyCascades)));
  }

}
