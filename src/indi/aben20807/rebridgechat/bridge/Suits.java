package indi.aben20807.rebridgechat.bridge;

public enum Suits {

	SPADES(4), HEARTS(3), DIAMONDS(2), CLUBS(1);

	private int suitValue;

	private Suits(int suitValue) {
		this.suitValue = suitValue;
	}

	public int getSuitValue() {
		return suitValue;
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
