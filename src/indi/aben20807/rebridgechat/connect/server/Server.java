package indi.aben20807.rebridgechat.connect.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.exception.ServerException;

public class Server {

	private CopyOnWriteArrayList<ObjectOutputStream> clientList;
	
	public Server() {

		System.out.println("Server: start....");
		System.out.println("Server: IP = " + getServerIP());
		clientList = new CopyOnWriteArrayList<>();
		System.out.println("Server: wait client connections....");
		try {
			collectClient();
		} catch (ServerException e) {
			e.printErrorMsg();
		}
		System.out.println("Server: room full....");
	}
	
	@SuppressWarnings("resource")
	private void collectClient() throws ServerException {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException e) {
			throw new ServerException(ErrorCode.SERVERSOCKET_CREATE_ERROR);
		}
		while(Server.this.clientList.size() < 4) {
			try (Socket socket = serverSocket.accept();){
				Server.this.clientList.add(new ObjectOutputStream(socket.getOutputStream()));
			} catch (IOException e) {
				throw new ServerException(ErrorCode.SOCKET_ACCEPT_ERROR);
			}
			System.out.println("Server: room client = " + Server.this.clientList.size());
		}
	}
	
	public String getServerIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		}
	}
}

class Channel implements Runnable{
	
	public void run() {
		
	}
}