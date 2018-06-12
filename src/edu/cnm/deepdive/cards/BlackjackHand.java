package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;
import java.awt.datatransfer.ClipboardOwner;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class BlackjackHand implements Comparable<BlackjackHand> {

  private static final int[] VALUES = {
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8,
      9,
      10,
      10,
      10,
      10,
  };

  private List<Card> hand;
  private Deck deck;
  private boolean aceInHand;
  private boolean soft;
  private boolean blackjack;
  private boolean busted;
  private int value;

  protected BlackjackHand(Deck deck) throws InsufficientCardsException {
    hand = new LinkedList<>();
    hand.addAll(Arrays.asList(deck.deal(2)));
    this.deck = deck;
    update();
  }

  private void update() {
    aceInHand = false;
    value = 0;
    soft = false;
    blackjack = false;
    busted = false;
    for (Card card : hand) {
      if (card.getRank() == Rank.ACE) {
        aceInHand = true;
      }
      value += VALUES[card.getRank().ordinal()];
    }
    if (value > 21) {
      value = 0;
      busted = true;
    } else if (value <= 11 && aceInHand) {
      value += 10;
      soft = true;
      if (hand.size() == 2 && value == 21) {
        blackjack = true;
      }
    }
  }

  protected final void hit() throws InsufficientCardsException {
    hand.add(deck.deal());
    update();
  }

  @Override
  public int compareTo(BlackjackHand other) {
    int comparison = Integer.compare(value, other.value);
    if (comparison == 0) {
      if (blackjack && !other.blackjack) {
        comparison = 1;
      } else if (other.blackjack && !blackjack) {
        comparison = -1;
      }
    }
    return comparison;
  }

  protected boolean isSoft() {
    return soft;
  }

  public boolean isBlackjack() {
    return blackjack;
  }

  public boolean isBusted() {
    return busted;
  }

  public int getValue() {
    return value;
  }

  public Card[] getHand() {
    return hand.toArray(new Card[hand.size()]);
  }

  public abstract void play() throws InsufficientCardsException;

  @Override
  public String toString() {
    String status;
    if (isBusted()) {
      status = "busted!";
    } else if (isBlackjack()) {
      status = "Blackjack!";
    } else {
      status = String.format("%d", getValue());
    }
    return String.format("Current hand: %s (%s)", Arrays.toString(getHand()), status);
  }

}














