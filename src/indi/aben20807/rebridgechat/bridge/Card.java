package indi.aben20807.rebridgechat.bridge;

import java.io.Serializable;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.exception.CardException;

public class Card implements Serializable {

  private static final long serialVersionUID = -2488863465746111038L;
  private Suits suit;
  private Ranks rank;

  public Card(Suits suit, Ranks rank) {
    try {
      setSuit(suit);
      setRank(rank);
    } catch (CardException e) {
      e.printErrorMsg();
    }
  }

  @Override
  public String toString() {
	  return ("" + this.suit + this.rank);
  }

  public Suits getSuit() {
    return this.suit;
  }

  public Ranks getRank() {
    return this.rank;
  }

  private void setSuit(Suits suit) throws CardException {
    if (Suits.contains(suit)) {
      this.suit = suit;
      return;
    }
    throw new CardException(ErrorCode.CARD_ARGUMENT_ERROR);
  }

  public void setRank(Ranks rank) throws CardException {
    if (Ranks.contains(rank)) {
      this.rank = rank;
      return;
    }
    throw new CardException(ErrorCode.CARD_ARGUMENT_ERROR);
  }
}
