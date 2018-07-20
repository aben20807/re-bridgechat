package indi.aben20807.rebridgechat.exception;

import indi.aben20807.rebridgechat.ErrorCode;

public class ServerException extends BridgechatException {

  private static final long serialVersionUID = 3456338736834629775L;

  public ServerException(ErrorCode code) {
    super(code);
  }
}
