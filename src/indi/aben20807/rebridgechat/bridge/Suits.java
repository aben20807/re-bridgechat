package indi.aben20807.rebridgechat.bridge;

public enum Suits {

	SPADES(4), HEARTS(3), DIAMONDS(2), CLUBS(1);

	private int suit;

	private Suits(int suit) {
		this.suit = suit;
	}

	public int getSuit() {
		return suit;
	}
	
	public String toString() {
		return ""+suit;
	}

	public static boolean contains(Suits suit) {
		for (Suits c : Suits.values()) {
			if (c.equals(suit)) {
				return true;
			}
		}
		return false;
	}
}
