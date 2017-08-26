package indi.aben20807.rebridgechat.connect.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.exception.ServerException;

public class Server {

}

class ClientCollection implements Runnable{
	
	public volatile boolean isRoomFull;
	private ServerSocket serverSocket = null;
	
	public ClientCollection() throws ServerException{
		isRoomFull = false;
		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException e) {
			throw new ServerException(ErrorCode.SERVERSOCKET_CREATE_ERROR);
		}
	}
	
	public void run() {
		Socket socket = null;
		
		while(!isRoomFull) {
			//socket = serverSocket.accept();
		}
	}
}

class Channel implements Runnable{
	
	public void run() {
		
	}
}