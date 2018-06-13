package edu.cnm.deepdive.cards;

import static org.junit.jupiter.api.Assertions.*;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;
import org.junit.jupiter.api.Test;

class BlackjackDealerHandTest {

  static final Card[] TEST_CARDS = {
      new Card(Rank.QUEEN, Suit.DIAMONDS),
      new Card(Rank.ACE, Suit.DIAMONDS),
      new Card(Rank.JACK, Suit.CLUBS),
      new Card(Rank.SEVEN, Suit.CLUBS),
      new Card(Rank.ACE, Suit.CLUBS),
      new Card(Rank.SIX, Suit.CLUBS),
  };

  @Test
  void play() throws InsufficientCardsException {
    Deck deck = new Deck(TEST_CARDS);
    BlackjackHand hand1 = new BlackjackDealerHand(deck);
    assertAll(
        "Pre-play, hit on soft 17",
        () -> assertEquals(17, hand1.getTotal()),
        () -> assertEquals(17, hand1.getValue()),
        () -> assertTrue(hand1.isSoft()),
        () -> assertFalse(hand1.isBusted()),
        () -> assertFalse(hand1.isBlackjack())
    );
    assertDoesNotThrow(hand1::play);
    assertAll(
        "Post-play, hit on soft 17",
        () -> assertEquals(24, hand1.getTotal()),
        () -> assertEquals(0, hand1.getValue()),
        () -> assertFalse(hand1.isSoft()),
        () -> assertTrue(hand1.isBusted()),
        () -> assertFalse(hand1.isBlackjack())
    );
    BlackjackHand hand2 = new BlackjackDealerHand(deck);
    assertDoesNotThrow(hand2::play);
    assertAll(
        "Post-play, blackjack",
        () -> assertEquals(21, hand2.getTotal()),
        () -> assertEquals(21, hand2.getValue()),
        () -> assertTrue(hand2.isSoft()),
        () -> assertFalse(hand2.isBusted()),
        () -> assertTrue(hand2.isBlackjack())
    );
    deck.gather();
    BlackjackHand hand3 = new BlackjackDealerHand(deck, false);
    assertDoesNotThrow(hand3::play);
    assertAll(
        "Post-play, don't hit on soft 17",
        () -> assertEquals(17, hand3.getTotal()),
        () -> assertEquals(17, hand3.getValue()),
        () -> assertTrue(hand3.isSoft()),
        () -> assertFalse(hand3.isBusted()),
        () -> assertFalse(hand3.isBlackjack())
    );
  }

  @Test
  void compareTo() throws InsufficientCardsException {
    Deck deck = new Deck(TEST_CARDS);
    BlackjackHand hand1 = new BlackjackDealerHand(deck);
    BlackjackHand hand2 = new BlackjackDealerHand(deck);
    assertAll(
        "Pre-play, hit on soft 17",
        () -> assertTrue(hand1.compareTo(hand2) == 0),
        () -> assertTrue(hand2.compareTo(hand1) == 0)
    );
    assertAll(
        "Play, hit on soft 17",
        () -> assertDoesNotThrow(hand1::play),
        () -> assertDoesNotThrow(hand2::play)
    );
    assertAll(
        "Post-play, hit on soft 17",
        () -> assertTrue(hand1.compareTo(hand2) > 0),
        () -> assertTrue(hand2.compareTo(hand1) < 0)
    );
    deck.gather();
    BlackjackHand hand3 = new BlackjackDealerHand(deck, false);
    BlackjackHand hand4 = new BlackjackDealerHand(deck, false);
    assertAll(
        "Pre-play, don't hit on soft 17",
        () -> assertTrue(hand3.compareTo(hand4) == 0),
        () -> assertTrue(hand4.compareTo(hand3) == 0)
    );
    assertAll(
        "Play, don't hit on soft 17",
        () -> assertDoesNotThrow(hand3::play),
        () -> assertDoesNotThrow(hand4::play)
    );
    assertAll(
        "Post-play, don't hit on soft 17",
        () -> assertTrue(hand3.compareTo(hand4) == 0),
        () -> assertTrue(hand4.compareTo(hand3) == 0)
    );
  }

}