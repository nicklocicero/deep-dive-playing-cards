package edu.cnm.deepdive.cards;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RankTest {

  private static final String[] RANK_SYMBOLS = {
      "A",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "J",
      "Q",
      "K",
  };

  @Test
  void testToString() {
    for (Rank rank : Rank.values()) {
      assertEquals(RANK_SYMBOLS[rank.ordinal()], rank.toString());
      System.out.println(rank);
    }
  }

}










