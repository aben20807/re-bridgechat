package indi.aben20807.rebridgechat.exception;

import indi.aben20807.rebridgechat.ErrorCode;

public class ClientException extends BridgechatException{

	private static final long serialVersionUID = -8753253120500907873L;

	public ClientException(ErrorCode code) {
		super(code);
	}
}