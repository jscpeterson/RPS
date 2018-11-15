package edu.cnm.deepdive.rps.model;

import java.util.Comparator;

public enum RpsBreed {
  ROCK("\u270a"),
  PAPER("\u270b"),
  SCISSORS("\u270c");

  private String symbol;

  RpsBreed(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }

  private static final int[][] DOMINANCE = {
      //            ROCK, PAPER, SCISSORS
      /*ROCK    */{    0,    -1,        1},
      /*PAPER   */{    1,     0,       -1},
      /*SCISSORS*/{   -1,     1,        0}
  };

  public static final Comparator<RpsBreed> REFEREE = new Comparator<RpsBreed>() {
    @Override
    public int compare(RpsBreed rps1, RpsBreed rps2) {
      return DOMINANCE[rps1.ordinal()][rps2.ordinal()];
    }
  };
}
