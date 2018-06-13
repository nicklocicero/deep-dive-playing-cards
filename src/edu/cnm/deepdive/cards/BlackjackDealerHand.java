package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;

/**
 * Concrete subclass of {@link BlackjackHand} implementing a playing algorithm
 * typical of casino dealers. Through a constructor argument, this class
 * supports either a "stand on 17 or higher, hit on 16 or lower" rule, or a
 * "stand on 18 or higher and hard 17, hit on 16 or lower and soft 17" rule; the
 * latter is more commonly used in casino play, and is thus the default behavior.
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public class BlackjackDealerHand extends BlackjackHand {

  private boolean hitOnSoft17;

  /**
   * Initializes this instance with the specified {@link Deck} of cards.
   * Invoking this constructor is equivalent to invoking {@link
   * #BlackjackDealerHand(Deck, boolean) BlackjackDealerHand(deck, true)}.
   *
   * @param deck                          source of cards for this instance.
   * @throws InsufficientCardsException   if <code>deck</code> doesn't have at
   *                                      least 2 undealt cards available.
   */
  public BlackjackDealerHand(Deck deck) throws InsufficientCardsException {
    this(deck, true);
  }

  /**
   * Initializes this instance with the specified {@link Deck} of cards, and
   * with the specified "hit on soft 17" rule flag.
   *
   * @param deck                          source of cards for this instance.
   * @param hitOnSoft17
   * @throws InsufficientCardsException   if <code>deck</code> doesn't have at
   *                                      least 2 undealt cards available.
   */
  public BlackjackDealerHand(Deck deck, boolean hitOnSoft17) throws InsufficientCardsException {
    super(deck);
    this.hitOnSoft17 = hitOnSoft17;
  }

  /**
   * Draws card ("hits") until the total of the cards in the hands is at least
   * 17 (if the "hit on soft 17" rule is not in force), or until the total is at
   * least 18 or a "hard" 17 (if the "hit on soft 17" rule is in force).
   *
   * @throws InsufficientCardsException   in the event that the {@link Deck}
   *                                      specified in the constructor invocation
   *                                      runs out of cards during play.
   */
  @Override
  public void play() throws Deck.InsufficientCardsException {
    while (
        getTotal() < 17
        || (
            getTotal() == 17
            && isSoft()
            && hitOnSoft17
        )
    ) {
      hit();
    }
  }

}
