package indi.aben20807.rebridgechat.connect.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.connect.Communicator;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.ClientException;
import indi.aben20807.rebridgechat.exception.CommunicatorException;

public class Client {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Message message;
	private boolean isReadyToSubmit;
	
	public Client() {
		isReadyToSubmit = false;
		try {
			connectToServer("127.0.0.1");
		} catch (ClientException e) {
			e.printErrorMsg();
			System.exit(0);
		}
		new Receiver();
		isReadyToSubmit = true;
	}
	
	public Message getMessage() {
		return message;
	}
	
	public void connectToServer(String serverIP) throws ClientException {
		try {
			socket = new Socket(serverIP, 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			waitRoomFull();
		} catch (IOException e) {
			throw new ClientException(ErrorCode.CONNECT_TO_SERVER_ERROR);
		}
	}
	
	private void waitRoomFull() {
		Message message;
		try {
			while((message = Communicator.readFromChannel(in)) != null) {
				if(message.getContent().equals(">succeed")) {
					System.out.println("get \">succeed\"");
					break;
				}
			}
		} catch (CommunicatorException e) {
			e.printErrorMsg();
		}
	}
	
	public void submitToServer(Message message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isReadyToSubmit == false) {
					System.out.flush();// mysterious power OuO
				}
				try {
					Communicator.writeToChannel(out, message);
				} catch (CommunicatorException e) {
					e.printErrorMsg();
				}
			}
		}).start();
	}
	
	@Override
	protected void finalize() throws Throwable {
		closeAll();
		super.finalize();
	}
	
	private void closeAll() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Receiver implements Runnable{
		
		Receiver(){
			new Thread(this, "Receiver").start();
		}
		
		public void run() {
			try {
				while((Client.this.message = Communicator.readFromChannel(Client.this.in)) != null) {
					System.out.println(Client.this.message.getContent());
				}
			} catch (CommunicatorException e) {
				e.printErrorMsg();
			} finally {
				Client.this.closeAll();
			}
		}
	}
}