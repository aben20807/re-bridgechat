package indi.aben20807.rebridgechat.exception;

import indi.aben20807.rebridgechat.ErrorCode;

public class CardException extends BridgechatException {

	private static final long serialVersionUID = -6093834835839240191L;
	
	public CardException(ErrorCode code) {
		super(code);
	}
}