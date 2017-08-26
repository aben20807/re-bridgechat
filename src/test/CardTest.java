package test;

import indi.aben20807.rebridgechat.bridge.Card;
import indi.aben20807.rebridgechat.bridge.Suits;
import indi.aben20807.rebridgechat.exception.CardException;

public class CardTest {

	public static void main(String[] args) throws CardException {

		Card card1 = new Card('T', Suits.CLUBS, 2);
		System.out.println(card1.getCardInfo());

		try {
			Card card2 = new Card('j', Suits.CLUBS, 10);
			System.out.println(card2.getCardInfo());
		} catch (CardException e) {
			System.out.println(e.getErrorMsg());
		}
	}
}