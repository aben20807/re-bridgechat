package indi.aben20807.rebridgechat.exception;

import indi.aben20807.rebridgechat.ErrorCode;

public class CardException extends Exception {

	private int errorCode;
	private String errorMsg;

	public CardException(ErrorCode code) {
		this.errorMsg = code.getMsg();
		this.errorCode = code.getCode();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
