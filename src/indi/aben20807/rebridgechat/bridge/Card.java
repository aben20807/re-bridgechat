package indi.aben20807.rebridgechat.bridge;

import java.io.Serializable;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.exception.CardException;

public class Card implements Serializable{

	private static final long serialVersionUID = -2488863465746111038L;
	private char point; // card point (2~9,T,J,Q,K,A)
	private Suits suit; // card suit (defined in Suits)
	private int value;  // 2~14

	public Card(char point, Suits suit, int value) {
		try {
			setPoint(point);
			setSuit(suit);
			setValue(value);
		} catch (CardException e) {
			e.printErrorMsg();
		}
	}
	
	@Override
	public String toString() {
		return getCardInfo();
	}

	public String getCardInfo() {
		return ("" + getPoint() + getSuitValue() + getValue());
	}

	public char getPoint() {
		return point;
	}

	private void setPoint(char point) throws CardException {
		if ((point >= '2' && point <= '9') || point == 'T' || point == 'J' || point == 'Q' || point == 'K'
				|| point == 'A') {
			this.point = point;
			return;
		}
		throw new CardException(ErrorCode.CARD_ARGUMENT_ERROR);
	}

	public int getSuitValue() {
		return suit.getSuitValue();
	}

	private void setSuit(Suits suit) throws CardException {
		if (Suits.contains(suit)) {
			this.suit = suit;
			return;
		}
		throw new CardException(ErrorCode.CARD_ARGUMENT_ERROR);
	}

	public int getValue() {
		return value;
	}

	private void setValue(int value) throws CardException {
		if (value >= 2 && value <= 14) {
			this.value = value;
			return;
		}
		throw new CardException(ErrorCode.CARD_ARGUMENT_ERROR);
	}
}