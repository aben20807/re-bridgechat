package indi.aben20807.rebridgechat.connect.client;

import java.io.IOException;
import java.net.Socket;

public class Client {

	public Client() {

		connectToServer("192.168.56.1");
	}
	
	public void connectToServer(String serverIP) {
		try {
			Socket socket = new Socket(serverIP, 8080);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
