package indi.aben20807.rebridgechat.connect;

import java.io.Serializable;

import indi.aben20807.rebridgechat.bridge.Card;

public class Message implements Serializable{

	private static final long serialVersionUID = 1371494515691412295L;
	private String text;
	private Card card;
	private MessageOption option;
	
	public Message(Object object, MessageOption option) {
		setOption(option);
		switch (option) {
			case CHAT:
			case CALL:
			case COMMAND:
				setText((String) object);
				break;
			case CARD:
				setCard((Card) object);
				break;
			default:
				break;
		}
	}
	
	public Message(String text) {
		setOption(MessageOption.COMMAND);
		setText(text);
	}
	
	public Object getContent() {
		switch (option) {
			case CHAT:
			case CALL:
			case COMMAND:
				return getText();
			case CARD:
				return getCard();
			default:
				return null;
		}
	}
	
	@Override
	public String toString() {
		switch (option) {
			case CHAT:
			case CALL:
			case COMMAND:
				return getText();
			case CARD:
				return getCard().getCardInfo();
			default:
				return "Message out of options";
		}
	}
	
	private String getText() {
		return text;
	}
	
	private void setText(String text) {
		this.text = text;
	}

	public MessageOption getOption() {
		return option;
	}

	private void setOption(MessageOption option) {
		this.option = option;
	}

	private Card getCard() {
		return card;
	}

	private void setCard(Card card) {
		this.card = card;
	}
}