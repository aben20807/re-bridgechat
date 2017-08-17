package indi.aben20807.rebridgechat;

public enum ErrorCode {

	CARD_ARGUMENT_ERROR(0, "argument error occur when creating card");

	private final int code;
	private final String msg;

	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + msg;
	}
}
