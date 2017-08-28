package indi.aben20807.rebridgechat;

public enum ErrorCode {

	CARD_ARGUMENT_ERROR(0, "Error when creating a card."),
	SERVERSOCKET_CREATE_ERROR(1, "Error when newing ServerSocket with port."),
	SOCKET_ACCEPT_ERROR(2, "Error when socket accept that cannot full room."),
	GET_SERVER_IP_ERROR(3, "Error when getting server's IP address."),
	CLIENT_SUBMIT_ERROR(4, "Error when subming message."),
	SERVER_BROADCAST_ERROR(5, "Error when broadcasting to member."),
	WRITE_TO_CHANNEL_ERROR(6, "Error when using Communicator to write message to channel."),
	READ_FROM_CHANNEL_ERROR(7, "Error when using Communicator to read message from channel.");
	
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