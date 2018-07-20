package indi.aben20807.rebridgechat.bridge;

public enum Ranks {
  _A(14),
  _K(13),
  _Q(12),
  _J(11),
  _T(10),
  _9(9),
  _8(8),
  _7(7),
  _6(6),
  _5(5),
  _4(4),
  _3(3),
  _2(2);

  private int rankValue;

  private Ranks(int rankValue) {
    this.rankValue = rankValue;
  }

  public int getRankValue() {
    return rankValue;
  }

  public static boolean contains(Ranks rank) {
    for (Ranks c : Ranks.values()) {
      if (c.equals(rank)) {
        return true;
      }
    }
    return false;
  }
}
