package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;

public class BlackjackDealerHand extends BlackjackHand {

  public BlackjackDealerHand(Deck deck) throws InsufficientCardsException {
    super(deck);
  }

  @Override
  public void play() throws Deck.InsufficientCardsException {
    while (getValue() < 17 && !isBusted()) {
      hit();
    }
  }

}
