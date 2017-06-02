package cs3500.hw04;

import cs3500.hw02.FreecellModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link FreecellModelCreator}.
 */
public class FreecellModelCreatorTest {
  // Tests for the create() method
  @Test(expected = IllegalArgumentException.class)
  public void createNullType() {
    FreecellModelCreator.create(null);
  }

  @Test
  public void createSingleMove() {
    assertTrue(FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE)
        instanceof FreecellModel);
  }

  @Test
  public void createMultiMove() {
    assertTrue(FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE)
        instanceof FreecellMultimoveModel);
  }
}
