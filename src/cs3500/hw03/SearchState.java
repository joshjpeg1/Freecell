package cs3500.hw03;

/**
 * Represents the different states that a move is in when looking for input.
 */
public enum SearchState {
  SOURCE_PILE("source pile type and number"), CARD_INDEX("source card index"),
  DEST_PILE("destination pile type and number"), FINISHED("");

  private String toString;

  /**
   * Constructs a {@code SearchState} object.
   *
   * @param toString    a String representation of this search state
   */
  SearchState(String toString) {
    this.toString = toString;
  }

  /**
   * Returns this {@code SearchState} as a String.
   *
   * @return a string representation of this SearchState
   */
  @Override
  public String toString() {
    return toString;
  }
}
