package edu.cnm.deepdive.cards;

/**
 * <code>Suit</code> is an enumeration of the 4 suits (clubs, diamonds, hearts,
 * spades) used in standard playing cards.
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public enum Suit {
  CLUBS("\u2663"),
  DIAMONDS("\u2666"),
  HEARTS("\u2665"),
  SPADES("\u2660");

  private String symbol;

  private Suit(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

}
