package indi.aben20807.rebridgechat.connect.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import indi.aben20807.rebridgechat.connect.Communicator;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.CommunicatorException;

public class Client {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Client() {
		connectToServer("192.168.56.1");
		new Receiver();
	}
	
	public void connectToServer(String serverIP) {
		try {
			socket = new Socket(serverIP, 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			waitRoomFull();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void waitRoomFull() {
		Message message;
		try {
			while((message = Communicator.readFromChannel(in)) != null) {
				if(message.getContent().equals(">succeed")) {
					break;
				}
			}
		} catch (CommunicatorException e) {
			e.printErrorMsg();
		}
	}
	
	public synchronized void submitToServer(Message message) {
		try {
			Communicator.writeToChannel(out, message);
		} catch (CommunicatorException e) {
			e.printErrorMsg();
		}
	}
	
	class Receiver implements Runnable{
		
		Receiver(){
			new Thread(this, "Receiver").start();
		}
		
		public void run() {
			Message message;
			try {
				while((message = Communicator.readFromChannel(in)) != null) {
					System.out.println(message.getContent());
				}
			} catch (CommunicatorException e) {
				e.printErrorMsg();
			} finally {
				try {
					out.close();
					in.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}