package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class ConsoleGame {

  public static void main(String... args) throws InsufficientCardsException {
    try (Scanner scanner = new Scanner(System.in)) {
      Random rng = new SecureRandom();
      Deck deck = new Deck();
      int pot = 100;
      int bet = -1;
      while (pot > 0 && bet != 0) {
        System.out.printf("You have $%d in chips.%n", pot);
        while (bet < 0) {
          System.out.printf("What is your bet? [0-%d] ", Math.min(10, pot));
          while (!scanner.hasNext()) {}
          if (scanner.hasNextInt()) {
            int input = scanner.nextInt();
            if (input >= 0 && input <= Math.min(10, pot)) {
              bet = input;
            }
          }
          scanner.nextLine();
        }
        if (bet > 0) {
          deck.shuffle(rng);
          BlackjackHand dealer = new BlackjackDealerHand(deck);
          BlackjackHand player = new InteractiveBlackjackHand(deck, scanner);
          System.out.printf("Dealer's top card: %s.%n", dealer.getHand()[1]);
          System.out.println("Your play:");
          player.play();
          dealer.play();
          System.out.printf("Dealer's hand: %s.%n", dealer);
          int comparison = player.compareTo(dealer);
          if (comparison < 0) {
            System.out.printf("You lost $%d!%n", bet);
            pot -= bet;
          } else if (comparison > 0) {
            System.out.printf("You won $%d!%n", bet);
            pot += bet;
          } else {
            System.out.printf("Push!%n");
          }
          bet = -1;
        }
      }
      System.out.printf("You leave the table with $%d in chips.%n", pot);
    }
  }

}















