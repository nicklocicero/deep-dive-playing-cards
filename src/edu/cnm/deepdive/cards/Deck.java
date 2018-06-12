package edu.cnm.deepdive.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Deck {

  private List<Card> cards;
  private List<Card> dealt;
  boolean shuffled;

  public Deck() {
    cards = new ArrayList<>(Rank.values().length * Suit.values().length);
    dealt = new LinkedList<>();
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        Card card = new Card(rank, suit);
        cards.add(card);
      }
    }
    shuffled = false;
  }

  public void shuffle(Random rng) {
    cards.addAll(dealt);
    dealt.clear();
    Collections.shuffle(cards, rng);
    shuffled = true;
  }

  public void sort() {
    Collections.sort(cards);
    shuffled = false;
  }

  public boolean isShuffled() {
    return shuffled;
  }

  public Card deal() throws InsufficientCardsException {
    Card card = null;
    try {
      card = cards.remove(cards.size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new InsufficientCardsException();
    }
    dealt.add(card);
    return card;
  }

  public Card[] deal(int numCards) throws InsufficientCardsException {
    Card[] cardsDealt = new Card[numCards];
    for (int i = 0; i < numCards; i++) {
      cardsDealt[i] = deal();
    }
    return cardsDealt;
  }

  public static class InsufficientCardsException extends Exception {

    private InsufficientCardsException() {
      super();
    }

  }

}
