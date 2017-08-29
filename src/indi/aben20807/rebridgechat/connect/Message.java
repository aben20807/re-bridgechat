package indi.aben20807.rebridgechat.connect;

import java.io.Serializable;

import indi.aben20807.rebridgechat.bridge.Card;

public class Message implements Serializable{

	private static final long serialVersionUID = 1371494515691412295L;
	private String content;
	private Card card;
	private MessageOption option;
	
	public Message(Object object, MessageOption option) {
		setOption(option);
		switch (option) {
			case CHAT:
			case CALL:
			case COMMAND:
				setContent((String) object);
				break;
			case CARD:
				setCard((Card) object);
				break;
			default:
				break;
		}
	}
	
	public Message(String content) {
		setContent(content);
	}
	
	@Override
	public String toString() {
		return getContent();
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public MessageOption getOption() {
		return option;
	}

	public void setOption(MessageOption option) {
		this.option = option;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}