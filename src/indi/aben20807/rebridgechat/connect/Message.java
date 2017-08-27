package indi.aben20807.rebridgechat.connect;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1371494515691412295L;
	private String content;
	
	public Message(String content) {
		setContent(content);
	}
	
//	@Override
//	public String toString() {
//		return getContent();
//	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}