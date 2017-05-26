package cs3500.hw03;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link SearchState}.
 */
public class SearchStateTest {
  // Tests for the toString() method
  @Test
  public void toStringSourcePile() {
    assertEquals("source pile type and number",
        SearchState.SOURCE_PILE.toString());
  }

  @Test
  public void toStringCardIndex() {
    assertEquals("source card index",
        SearchState.CARD_INDEX.toString());
  }

  @Test
  public void toStringDestPile() {
    assertEquals("destination pile type and number",
        SearchState.DEST_PILE.toString());
  }

  @Test
  public void toStringFinished() {
    assertEquals("",
        SearchState.FINISHED.toString());
  }
}
