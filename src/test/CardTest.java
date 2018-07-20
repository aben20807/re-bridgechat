package test;

import indi.aben20807.rebridgechat.bridge.Card;
import indi.aben20807.rebridgechat.bridge.Ranks;
import indi.aben20807.rebridgechat.bridge.Suits;
import indi.aben20807.rebridgechat.exception.CardException;

public class CardTest {

  public static void main(String[] args) throws CardException {

    Card card1 = new Card(Suits.CLUBS, Ranks._2);
    System.out.println(card1);
    System.out.println(card1.getCardInfo());

    Card card2 = new Card(Suits.SPADES, Ranks._A);
    System.out.println(card2);
    System.out.println(card2.getCardInfo());
  }
}
