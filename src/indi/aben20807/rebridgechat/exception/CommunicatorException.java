package indi.aben20807.rebridgechat.exception;

import indi.aben20807.rebridgechat.ErrorCode;

public class CommunicatorException extends BridgechatException {

  private static final long serialVersionUID = 5975864995686402767L;

  public CommunicatorException(ErrorCode code) {
    super(code);
  }
}
