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
		linkBroadcasterToReceivers();
		System.out.println("Server: channels have been created....");
		try {
			ObjectOutputStream out = new ObjectOutputStream(clientList.get(0).getOutputStream());
			out.writeObject(new Message("S"));
			out = new ObjectOutputStream(clientList.get(1).getOutputStream());
			out.writeObject(new Message("SS"));
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
				System.out.println("Server: room size = " + clientList.size());
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
	
	public void linkBroadcasterToReceivers() {
		for(Socket socket : clientList) {
			new Broadcaster(socket);
		}
	}
	
	class Broadcaster implements Runnable{
		
		private Socket socket;
		
		Broadcaster(Socket socket){
			this.socket = socket;
			new Thread(this).start();
		}
	
		public void run() {
			Message message;
			try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
				while ((message = (Message) in.readObject()) != null) {
					System.out.println(message);
//					broadcast(message, Server.this.clientList);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}// catch (ServerException e) {
//				e.printErrorMsg();
//			}
		}
		
		public void broadcast(Message message, List<Socket> memberList) throws ServerException {
			for (Socket i : memberList) {
				try {
					straightTransmit(message, new ObjectOutputStream(i.getOutputStream()));
				} catch (IOException e) {
					throw new ServerException(ErrorCode.SERVER_BROADCAST_ERROR);
				}
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