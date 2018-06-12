package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;
import java.util.Arrays;
import java.util.Scanner;

public class InteractiveBlackjackHand extends BlackjackHand {

  private Scanner scanner;

  public InteractiveBlackjackHand(Deck deck, Scanner scanner)
      throws InsufficientCardsException {
    super(deck);
    this.scanner = scanner;
  }

  @Override
  public void play() throws InsufficientCardsException {
    boolean stay = false;
    while (!isBusted() && (getValue() < 21) && !stay) {
      System.out.println(this);
      Boolean hit = null;
      while (hit == null) {
        System.out.print("Hit? [Y/n] ");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.isEmpty() || input.charAt(0) == 'y') {
          hit = true;
          hit();
        } else if (input.charAt(0) == 'n') {
          hit = false;
          stay = true;
        }
      }
    }
    if (!stay) {
      System.out.println(this);
    }
  }

}
