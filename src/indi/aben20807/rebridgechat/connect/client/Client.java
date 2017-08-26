package indi.aben20807.rebridgechat.connect.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.ClientException;

public class Client {

	private Socket socket;
	
	public Client() {
		connectToServer("192.168.56.1");
		new Receiver();
	}
	
	public void connectToServer(String serverIP) {
		try {
			socket = new Socket(serverIP, 8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void submit(Message message) throws ClientException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			throw new ClientException(ErrorCode.CLIENT_SUBMIT_ERROR);
		}
	}
	
	class Receiver implements Runnable{
		
		Receiver(){
			new Thread(this).start();
		}
		
		public void run() {
			Message message;
			try (ObjectInputStream in = new ObjectInputStream(Client.this.socket.getInputStream());){
				while ((message = (Message) in.readObject()) != null) {
					System.out.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}