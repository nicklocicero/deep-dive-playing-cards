package edu.cnm.deepdive.cards;

/**
 * <code>Rank</code> is an enumeration of the ranks used in standard playing
 * cards. While each enumerated value has a value returned by the {@link
 * #ordinal()} method, corresponding to the given enumerated value's position in
 * the declaration list, this value should not be considered meaningful in a
 * gaming sense; on the other hand, the ordinal value of each can be useful for
 * indexing into game-specific value arrays.
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public enum Rank {
  ACE("A"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9"),
  TEN("10"),
  JACK("J"),
  QUEEN("Q"),
  KING("K");

  private String symbol;

  private Rank(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

}
