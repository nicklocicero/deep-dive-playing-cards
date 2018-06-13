package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class implements a console-mode, one-player (plus computer dealer)
 * Blackjack game. Only basic game play and betting actions are supported; in
 * particular, doubling down and splitting are not supported at all, and only a
 * limited form of insurance is supported.
 *
 * @author Nicholas Bennett &amp; Deep Dive Coding Java Cohort 4
 */
public class ConsoleGame {

  private static final int INITIAL_POT = 100;
  private static final int MAX_BET = 10;
  private static final Pattern NON_WHITE_SPACE = Pattern.compile("\\S+");

  /**
   *
   * @param args
   */
  public static void main(String... args) {
    try (Scanner scanner = new Scanner(System.in)) {
      Random rng = new SecureRandom();
      Deck deck = new Deck();
      int pot = INITIAL_POT;
      for (boolean play = true; play; play &= pot > 0) {
        System.out.printf("%nYou have $%d. ", pot);
        int bet = getBet(scanner, pot);
        if (bet > 0) {
          deck.gather();
          deck.shuffle(rng);
          BlackjackHand dealer = new BlackjackDealerHand(deck);
          BlackjackHand player = new InteractiveBlackjackHand(deck, scanner);
          Card topCard = dealer.getHand()[1];
          System.out.printf("%nDealer's top card: %s.%n", topCard);
          if (!player.isBlackjack()
              || (topCard.getRank() != Rank.ACE)
              || !buyInsurance(scanner, player)) {
            pot += playHands(scanner, player, dealer, bet);
          }
        } else {
          play = false;
        }
      }
      System.out.printf("You leave the table with $%d.%n", pot);
    } catch (InsufficientCardsException e) {
      /*
      In this program, this exception should never occur. If it does, wrap it in
      and throw a RuntimeException, terminating the program.
      */
      throw new RuntimeException(e);
    }
  }

  private static int getBet(Scanner scanner, int pot) {
    int bet = -1;
    int maxBet = Math.min(10, MAX_BET);
    do {
      System.out.printf("What is your bet? [0-%d] ", maxBet);
      while (!scanner.hasNext()) {}
      if (scanner.hasNextInt()) {
        int input = scanner.nextInt();
        if (input >= 0 && input <= maxBet) {
          bet = input;
        }
      }
      scanner.nextLine();
    } while (bet < 0);
    return bet;
  }

  private static boolean buyInsurance(Scanner scanner, BlackjackHand player) {
    Boolean insure = null;
    System.out.println(player);
    while (insure == null) {
      System.out.print("Take even money against dealer's possible blackjack? [y/n] ");
      while (!scanner.hasNext(NON_WHITE_SPACE)) {}
      char input = scanner.next(NON_WHITE_SPACE).toLowerCase().charAt(0);
      if (input == 'y') {
        insure = true;
      } else if (input == 'n') {
        insure = false;
      }
      scanner.nextLine();
    }
    return insure;
  }

  private static int playHands(Scanner scanner, BlackjackHand player, BlackjackHand dealer, int bet)
      throws InsufficientCardsException {
    int gain = 0;
    System.out.printf("%nYour play:%n");
    player.play();
    System.out.printf("%nDealer's play:%n");
    if (!player.isBusted()) {
      dealer.play();
    }
    System.out.printf("\t%s%n", dealer);
    int comparison = player.compareTo(dealer);
    if (comparison > 0) {
      gain = player.isBlackjack() ? bet * 3 / 2 : bet;
      System.out.printf("%nYou won $%d!%n", gain);
    } else if (comparison < 0 || dealer.isBlackjack()) {
      gain = -bet;
      System.out.printf("%nYou lost $%d!%n", bet);
    } else {
      System.out.printf("%nPush!%n");
    }
    return gain;
  }

}
