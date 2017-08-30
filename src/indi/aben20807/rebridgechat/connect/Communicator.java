package indi.aben20807.rebridgechat.connect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.exception.CommunicatorException;

public abstract class Communicator {

	public static Message readFromChannel(ObjectInputStream in) throws CommunicatorException {
		Object object = null;
		if(in != null) {
			try {
				object = in.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// e.printStackTrace();
				throw new CommunicatorException(ErrorCode.READ_FROM_CHANNEL_ERROR);
			}
		}
		if(object instanceof Message) {
			return (Message) object;
		}
		else {
			throw new CommunicatorException(ErrorCode.READ_FROM_CHANNEL_ERROR);
		}
	}
	
	public static void writeToChannel(ObjectOutputStream out, Message message) throws CommunicatorException {
		if(out != null) {
			try {
				out.writeObject(message);
				out.flush();
			} catch (IOException e) {
				throw new CommunicatorException(ErrorCode.WRITE_TO_CHANNEL_ERROR);
			}
		}
		else {
			throw new CommunicatorException(ErrorCode.WRITE_TO_CHANNEL_ERROR);
		}
	}
}