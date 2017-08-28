package indi.aben20807.rebridgechat.connect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Communicator {

	public Message readFromChannel(ObjectInputStream in) {
		Object object = null;
		try {
			object = in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(object instanceof Message) {
			return (Message) object;
		}
		else {
			return null;
		}
	}
	
	public void writeChannel(ObjectOutputStream out, Message message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}