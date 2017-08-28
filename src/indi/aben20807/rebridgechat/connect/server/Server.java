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
import indi.aben20807.rebridgechat.connect.Communicator;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.CommunicatorException;
import indi.aben20807.rebridgechat.exception.ServerException;

public class Server {

	private CopyOnWriteArrayList<Socket> clientList;
	private CopyOnWriteArrayList<ObjectOutputStream> objectOutputStreamList;
	private CopyOnWriteArrayList<ObjectInputStream> objectInputStreamList;
	
	public Server() {
		System.out.println("Server: start....");
		try {
			System.out.println("Server: IP = " + getServerIP());
		} catch (ServerException e1) {
			e1.printErrorMsg();
		}
		clientList = new CopyOnWriteArrayList<>();
		objectOutputStreamList = new CopyOnWriteArrayList<>();
		objectInputStreamList = new CopyOnWriteArrayList<>();
		try {
			collectClient();
		} catch (ServerException e2) {
			e2.printErrorMsg();
		}
		linkBroadcasterToReceivers();
	}
	
	private void collectClient() throws ServerException {
		System.out.println("Server: wait client connections....");
		try (ServerSocket serverSocket = new ServerSocket(8080);){
			while(Server.this.clientList.size() < 4) {
				try {
					Socket socket = serverSocket.accept();
					clientList.add(socket);
				} catch (IOException e) {
					throw new ServerException(ErrorCode.SOCKET_ACCEPT_ERROR);
				}
				System.out.println("Server: room size = " + clientList.size());
			}
		} catch (IOException e) {
			throw new ServerException(ErrorCode.SERVERSOCKET_CREATE_ERROR);
		}
		System.out.println("Server: room full");
	}
	
	public String getServerIP() throws ServerException {
		try {
			return InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			throw new ServerException(ErrorCode.GET_SERVER_IP_ERROR);
		}
	}
	
	public void linkBroadcasterToReceivers() {
		try {
			for(Socket socket : clientList) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStreamList.add(out);
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				objectInputStreamList.add(in);
				new Broadcaster(in);
			}
			System.out.println("Server: channels have been created");
			for(ObjectOutputStream out : objectOutputStreamList) {
				Communicator.writeToChannel(out, new Message(">succeed"));
			}
			System.out.println("Server: emit \">succeed\" to clients");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CommunicatorException e) {
			e.printErrorMsg();
		}
	}
	
	class Broadcaster implements Runnable{
		
		private ObjectInputStream in;
		
		Broadcaster(ObjectInputStream in){
			this.in = in;
			new Thread(this, "Broadcaster").start();
		}
	
		public void run() {
			Message message;
			try {
				while((message = Communicator.readFromChannel(in)) != null) {
					System.out.println(message.getContent());
					broadcast(message, Server.this.objectOutputStreamList);
				}
			} catch (CommunicatorException e) {
				e.printErrorMsg();
			} catch (ServerException e) {
				e.printErrorMsg();
			}
		}
		
		public void broadcast(Message message, List<ObjectOutputStream> objectOutputStreamList) throws ServerException {
			for (ObjectOutputStream i : objectOutputStreamList) {
				straightTransmit(i, message);
			}
		}
		
		public synchronized void straightTransmit(ObjectOutputStream out, Message message){
			try {
				Communicator.writeToChannel(out, message);
			} catch (CommunicatorException e) {
				e.printErrorMsg();
			}
		}
	}
}