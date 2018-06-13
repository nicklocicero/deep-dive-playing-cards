package edu.cnm.deepdive.cards;

/**
 * <code>Card</code> is a composition class, where the immutable object state
 * consists of the combination of a value from the {@link Rank}
 * <code>enum</code> and a value from the {@link Suit} <code>enum</code>.
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public class Card implements Comparable<Card> {

  private Rank rank;
  private Suit suit;

  /**
   * Initializes the <code>Card</code> instance with the specified {@link Rank}
   * and {@link Suit} values.
   *
   * @param rank  card rank.
   * @param suit  card suit.
   */
  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * Returns the rank of this instance.
   *
    * @return     card rank.
   */
  public Rank getRank() {
    return rank;
  }

  /**
   * Returns the suit of this instance.
   *
   * @return     card rank.
   */
  public Suit getSuit() {
    return suit;
  }

  @Override
  public int compareTo(Card other) {
    int comparison = suit.compareTo(other.suit);
    if (comparison == 0) {
      comparison = rank.compareTo(other.rank);
    }
    return comparison;
  }

  @Override
  public String toString() {
    return String.format("%s%s", rank, suit);
  }

}
