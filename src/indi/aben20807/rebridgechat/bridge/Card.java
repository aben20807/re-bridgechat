package indi.aben20807.rebridgechat.bridge;

public class Card {

	private int value; // 2~14
	private char number;// card point (2~9,T,J,Q,K,A)
	private Suits suit; // card suit (1~4, defined in Suits)

	public Card(int value, char number, Suits suit) {
		this.value = value;
		this.number = number;
		this.suit = suit;
	}
	
	public String getCardInfo() {
		return (""+getNumber()+getSuit()+getValue());
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (value >= 1 && value <= 52) {
			this.value = value;
		}
	}

	public char getNumber() {
		return number;
	}

	public void setNumber(char number) {
		if ((number >= '2' && number <= '9') || number == 'T' || number == 'J' || number == 'Q' || number == 'K'
				|| number == 'A') {
			this.number = number;
		}
	}

	public Suits getSuit() {
		return suit;
	}

	public void setSuit(Suits suit) {
		if (Suits.contains(suit)) {
			this.suit = suit;
		}
	}
}
