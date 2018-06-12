package edu.cnm.deepdive.cards;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class DeckTest {

  private Card[] unshuffledCards;
  private Card[] shuffledCards;

  @Test
  void deal() {
    Deck deck = new Deck();
    Random rng = new SecureRandom();
    assertDoesNotThrow(new Executable() {
      @Override
      public void execute() throws Throwable {
        unshuffledCards = deck.deal(Suit.values().length * Rank.values().length);
      }
    });
    assertThrows(Deck.InsufficientCardsException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        deck.deal();
      }
    });
    deck.shuffle(rng);
    assertDoesNotThrow(new Executable() {
      @Override
      public void execute() throws Throwable {
        shuffledCards = deck.deal(Suit.values().length * Rank.values().length);
      }
    });
    assertThrows(Deck.InsufficientCardsException.class, new Executable() {
      @Override
      public void execute() throws Throwable {
        deck.deal();
      }
    });
    Set<Card> unshuffledSet = Set.of(unshuffledCards);
    Set<Card> shuffledSet = Set.of(shuffledCards);
    assertEquals(unshuffledSet, shuffledSet);
  }

}













