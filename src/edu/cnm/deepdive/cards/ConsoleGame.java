package edu.cnm.deepdive.cards;

import edu.cnm.deepdive.cards.Deck.InsufficientCardsException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.ResourceBundle;
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

  public static ResourceBundle bundle;
  public static final String RESOURCE_BUNDLE = "resources/console_game";
  public static final String POT_AMOUNT_KEY = "pot_amount_pattern";
  public static final String DEALERS_TOP_CARD_KEY = "dealers_top_card_pattern";
  public static final String AMOUNT_WHEN_LEAVING_TABLE_KEY = "amount_when_leaving_table_pattern";
  public static final String PLAYERS_BET_KEY = "players_bet_pattern";
  public static final String BUY_INSURANCE_QUESTION_KEY = "buy_insurance_question_pattern";
  public static final String USER_CHOOSES_YES_KEY = "user_chooses_yes_pattern";
  public static final String USER_CHOOSES_NO_KEY = "user_chooses_no_pattern";
  public static final String PLAYERS_PLAY_KEY = "players_play_pattern";
  public static final String DEALERS_PLAY_KEY = "dealers_play_pattern";
  public static final String PLAYERS_WINNINGS_KEY = "players_winnings_pattern";
  public static final String PLAYERS_LOSS_KEY = "players_loss_pattern";
  public static final String PUSH_KEY = "push_pattern";

  // static initializer block, get used to it 😉
  static {
    bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
  }


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
        System.out.printf(bundle.getString(POT_AMOUNT_KEY), pot);
        int bet = getBet(scanner, pot);
        if (bet > 0) {
          deck.gather();
          deck.shuffle(rng);
          BlackjackHand dealer = new BlackjackDealerHand(deck);
          BlackjackHand player = new InteractiveBlackjackHand(deck, scanner);
          Card topCard = dealer.getHand()[1];
          System.out.printf(bundle.getString(DEALERS_TOP_CARD_KEY), topCard);
          if (!player.isBlackjack()
              || (topCard.getRank() != Rank.ACE)
              || !buyInsurance(scanner, player)) {
            pot += playHands(scanner, player, dealer, bet);
          }
        } else {
          play = false;
        }
      }
      System.out.printf(bundle.getString(AMOUNT_WHEN_LEAVING_TABLE_KEY), pot);
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
      System.out.printf(bundle.getString(PLAYERS_BET_KEY), maxBet);
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
      System.out.print(bundle.getString(BUY_INSURANCE_QUESTION_KEY));
      while (!scanner.hasNext(NON_WHITE_SPACE)) {}
      char input = scanner.next(NON_WHITE_SPACE).toLowerCase().charAt(0);
      if (input == bundle.getString(USER_CHOOSES_YES_KEY).charAt(0)) {
        insure = true;
      } else if (input == bundle.getString(USER_CHOOSES_NO_KEY).charAt(0)) {
        insure = false;
      }
      scanner.nextLine();
    }
    return insure;
  }

  private static int playHands(Scanner scanner, InteractiveBlackjackHand player, BlackjackHand dealer, int bet)
      throws InsufficientCardsException {
    int gain = 0;
    System.out.printf(bundle.getString(PLAYERS_PLAY_KEY));
    player.play();
    System.out.printf(bundle.getString(DEALERS_PLAY_KEY));
    if (!player.isBusted()) {
      dealer.play();
    }
    System.out.printf("\t%s%n", dealer);
    int comparison = player.compareTo(dealer);
    if (comparison > 0) {
      gain = player.isBlackjack() ? bet * 3 / 2 : bet;
      gain = player.isDoubleDown() ? gain * 2;
      System.out.printf(bundle.getString(PLAYERS_WINNINGS_KEY), gain);
    } else if (comparison < 0 || dealer.isBlackjack()) {
      gain = player.isDoubleDown() ? -bet * 2 : -bet;
      System.out.printf(bundle.getString(PLAYERS_LOSS_KEY), bet);
    } else {
      System.out.printf(bundle.getString(PUSH_KEY));
    }
    return gain;
  }

}
