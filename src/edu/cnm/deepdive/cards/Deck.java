package edu.cnm.deepdive.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

/**
 * <code>Deck</code> encapsulates a single deck of standard playing cards, with
 * methods for shuffling, sorting (to original, "factory" order), and dealing
 * cards.
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public class Deck {

  private Stack<Card> cards = new Stack<>();
  private Deque<Card> dealt = new LinkedList<>();
  boolean shuffled = false;

  /**
   * Initializes this instance with one card for each distinct combination of
   * {@link Rank} and {@link Suit} values. Note that the deck is not shuffled
   * automatically.
   */
  public Deck() {
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        Card card = new Card(rank, suit);
        cards.add(card);
      }
    }
  }

  /**
   * Initializes this instance with a specified sequence of cards, for the
   * purpose of unit testing. For example, a "fixed" deck could be passed
   * to the {@link BlackjackDealerHand#BlackjackDealerHand(Deck)} constructor,
   * to ensure that the play actions taken match those expected. Note that since
   * {@link #deal()} operates in LIFO order, the cards will be dealt in the
   * reverse order to that specified here.
   *
   * @param cards   specific cards to appear in the deck.
   */
  Deck(Card... cards) {
    this.cards.addAll(Arrays.asList(cards));
  }

  /**
   * Shuffles the undealt contents of the deck using the supplied source of
   * randomness.
   *
   * @param rng   random number generator.
   */
  public void shuffle(Random rng) {
    Collections.shuffle(cards, rng);
    shuffled = true;
  }

  /**
   * Collects all dealt cards back into the deck.
   */
  public void gather() {
    cards.addAll(dealt);
    dealt.clear();
  }

  /**
   * Sorts the undealt contents of the deck back into "factory" order
   * (alphabetically by suit, then by ascending rank order within suit).
   */
  public void sort() {
    Collections.sort(cards);
    shuffled = false;
  }

  /**
   * Returns shuffled flag.
   *
   * @return  flag indicating whether deck is shuffled.
   */
  public boolean isShuffled() {
    return shuffled;
  }

  /**
   * Removes and returns a single {@link Card} instance from the top of the deck.
   *
   * @return                              single card.
   * @throws InsufficientCardsException   when 1 or more of
   *                                      <code>numCards</code> cannot be dealt,
   *                                      because the deck is exhausted.
   */
  public Card deal() throws InsufficientCardsException {
    try {
      Card card = cards.pop();
      dealt.addFirst(card);
      return card;
    } catch (EmptyStackException e) {
      throw new InsufficientCardsException();
    }
  }

  /**
   * Removes <code>numCards</code> {@link Card Cards} from the top of the deck
   * and returns them in an array.
   *
   * @param numCards                    number of {@link Card} instances
   *                                    requested.
   * @return                            cards dealt from the deck.
   * @throws InsufficientCardsException when 1 or more of <code>numCards</code>
   *                                    cannot be dealt, because the deck is
   *                                    exhausted.
   */
  public Card[] deal(int numCards) throws InsufficientCardsException {
    Card[] cardsDealt = new Card[numCards];
    for (int i = 0; i < numCards; i++) {
      cardsDealt[i] = deal();
    }
    return cardsDealt;
  }

  /**
   * Exception type thrown when an attempt is made to deal one or more cards
   * from an empty deck.
   */
  public static class InsufficientCardsException extends Exception {

    private InsufficientCardsException() {
      super();
    }

  }

}
