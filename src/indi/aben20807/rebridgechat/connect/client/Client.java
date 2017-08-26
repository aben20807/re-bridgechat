package indi.aben20807.rebridgechat.connect.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

	private Socket socket;
	public Client() {

		connectToServer("192.168.56.1");
		new Channel();
	}
	
	public void connectToServer(String serverIP) {
		try {
			socket = new Socket(serverIP, 8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Channel implements Runnable{
		
		Channel(){
			
			new Thread(this).start();
		}
		
		public void run() {
			
			Object object;
			try (ObjectInputStream in = new ObjectInputStream(Client.this.socket.getInputStream());){
				while ((object = in.readObject()) != null) {
					System.out.println(object);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
