package indi.aben20807.rebridgechat.connect.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.ServerException;

public class Server {

	private CopyOnWriteArrayList<Socket> clientList;
	
	public Server() {
		System.out.println("Server: start....");
		try {
			System.out.println("Server: IP = " + getServerIP());
		} catch (ServerException e1) {
			e1.printErrorMsg();
		}
		clientList = new CopyOnWriteArrayList<>();
		System.out.println("Server: wait client connections....");
		try {
			collectClient();
		} catch (ServerException e2) {
			e2.printErrorMsg();
		}
		System.out.println("Server: room full....");
		createChannel();
		System.out.println("Server: channels have been created....");
		try {
			ObjectOutputStream out = new ObjectOutputStream(clientList.get(0).getOutputStream());
			out.writeObject("S");
			out = new ObjectOutputStream(clientList.get(1).getOutputStream());
			out.writeObject("SS");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void collectClient() throws ServerException {
		try (ServerSocket serverSocket = new ServerSocket(8080);){
			while(Server.this.clientList.size() < 4) {
				try {
					Socket socket = serverSocket.accept();
					Server.this.clientList.add(socket);
				} catch (IOException e) {
					throw new ServerException(ErrorCode.SOCKET_ACCEPT_ERROR);
				}
				System.out.println("Server: room client = " + Server.this.clientList.size());
			}
		} catch (IOException e) {
			throw new ServerException(ErrorCode.SERVERSOCKET_CREATE_ERROR);
		}
	}
	
	public String getServerIP() throws ServerException {
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			throw new ServerException(ErrorCode.GET_SERVER_IP_ERROR);
		}
	}
	
	public void createChannel() {
		for(Socket socket : clientList) {
			new Channel(socket);
		}
	}
	
	class Channel implements Runnable{
		
		private Socket socket;
		
		Channel(Socket socket){
			this.socket = socket;
			new Thread(this).start();
		}
	
		public void run() {
			String message;
			try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
				while ((message = (String) in.readObject()) != null) {
					System.out.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public void broadcast(Message message, List<ObjectOutputStream> memberList) {
			for (ObjectOutputStream i : memberList) {
				straightTransmit(message, i);
			}
		}
		
		public void straightTransmit(Message message, ObjectOutputStream out){
			try {
				out.writeObject(message);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}