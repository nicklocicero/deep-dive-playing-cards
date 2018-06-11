package edu.cnm.deepdive.cards;

public enum Suit {
  CLUBS("\u2663"),
  DIAMONDS("\u2666"),
  HEARTS("\u2665"),
  SPADES("\u2660");

  private String symbol;

  private Suit(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

}
