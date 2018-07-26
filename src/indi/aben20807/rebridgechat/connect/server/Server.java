package indi.aben20807.rebridgechat.connect.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.connect.Communicator;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.CommunicatorException;
import indi.aben20807.rebridgechat.exception.ServerException;

public class Server {

  private static Server instance = null;
  private CopyOnWriteArrayList<Socket> clientList;
  private CopyOnWriteArrayList<ObjectOutputStream> objectOutputStreamList;
  private CopyOnWriteArrayList<ObjectInputStream> objectInputStreamList;
  final ExecutorService executorService = Executors.newCachedThreadPool();

  private Server() {
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

  public static Server getInstance() {
    if (instance == null) {
      synchronized (Server.class) {
        if (instance == null) {
          instance = new Server();
        }
      }
    }
    return instance;
  }

  @Override
  protected void finalize() throws Throwable {
    closeAll();
    super.finalize();
  }

  private void closeAll() {
    if (executorService != null) {
      executorService.shutdown();
    }
    try {
      for (ObjectOutputStream out : objectOutputStreamList) {
        if (out != null) out.close();
      }
      for (ObjectInputStream in : objectInputStreamList) {
        if (in != null) in.close();
      }
      for (Socket socket : clientList) {
        if (socket != null) socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void collectClient() throws ServerException {
    System.out.println("Server: wait client connections....");
    try (ServerSocket serverSocket = new ServerSocket(8080); ) {
      while (Server.this.clientList.size() < 4) {
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
    try (final DatagramSocket socket = new DatagramSocket()) {
      socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
      return socket.getLocalAddress().getHostAddress().toString();
    } catch (UnknownHostException | SocketException e) {
      throw new ServerException(ErrorCode.GET_SERVER_IP_ERROR);
    }
  }

  public void linkBroadcasterToReceivers() {
    try {
      for (Socket socket : clientList) {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStreamList.add(out);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        objectInputStreamList.add(in);
        executorService.execute(new Broadcaster(in));
      }
      System.out.println("Server: channels have been created");
      for (ObjectOutputStream out : objectOutputStreamList) {
        Communicator.writeToChannel(out, new Message(">succeed"));
      }
      System.out.println("Server: emit \">succeed\" to clients");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (CommunicatorException e) {
      e.printErrorMsg();
    }
  }

  class Broadcaster implements Runnable {

    private ObjectInputStream in;

    Broadcaster(ObjectInputStream in) {
      this.in = in;
      new Thread(this, "Broadcaster");
    }

    public void run() {
      Message message;
      try {
        while ((message = Communicator.readFromChannel(in)) != null) {
          System.out.println(message.getContent());
          broadcast(message, Server.this.objectOutputStreamList);
        }
      } catch (CommunicatorException e) {
        e.printErrorMsg();
      } catch (ServerException e) {
        e.printErrorMsg();
      } finally {
        Server.this.closeAll();
      }
    }

    public void broadcast(Message message, List<ObjectOutputStream> objectOutputStreamList)
        throws ServerException {
      for (ObjectOutputStream out : objectOutputStreamList) {
        try {
          Communicator.writeToChannel(out, message);
        } catch (CommunicatorException e) {
          e.printErrorMsg();
        }
      }
    }
  }
}
