package indi.aben20807.rebridgechat.exception;

import indi.aben20807.rebridgechat.ErrorCode;

public abstract class BridgechatException extends Exception{

	private static final long serialVersionUID = -5069771817962750237L;
	private int errorCode;
	private String errorMsg;

	public BridgechatException(ErrorCode code) {
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
