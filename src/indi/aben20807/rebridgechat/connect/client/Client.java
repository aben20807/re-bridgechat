package indi.aben20807.rebridgechat.connect.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import indi.aben20807.rebridgechat.ErrorCode;
import indi.aben20807.rebridgechat.connect.Communicator;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.exception.ClientException;
import indi.aben20807.rebridgechat.exception.CommunicatorException;

public class Client {

  private static Client instance = null;
  private Socket socket;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private Message message;
  private boolean isReadyToSubmit;
  private Queue<Message> outq;
  private Queue<Message> inq;

  private Client() {
    isReadyToSubmit = false;
    outq = new ConcurrentLinkedQueue<>();
    inq = new ConcurrentLinkedQueue<>();
    try {
      connectToServer("127.0.0.1");
    } catch (ClientException e) {
      e.printErrorMsg();
      System.exit(0);
    }
    new Receiver();
    isReadyToSubmit = true;
  }

  public static Client getInstance() {
    if (instance == null) {
      synchronized (Client.class) {
        if (instance == null) {
          instance = new Client();
        }
      }
    }
    return instance;
  }

  public Message getMessage() {
    return inq.poll();
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
      while (true) {
        if (((message = Communicator.readFromChannel(in)) != null)
            && (message.getContent().equals(">succeed"))) {
          System.out.println("get \">succeed\"");
          break;
        }
      }
    } catch (CommunicatorException e) {
      e.printErrorMsg();
    }
  }

  public void submitMessage(Message message) {
    if (isReadyToSubmit == true) {
      outq.add(message);
      submitToServer();
    }
  }

  private void submitToServer() {
    new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  while (!outq.isEmpty()) {
                    message = outq.poll();
                    Communicator.writeToChannel(out, message);
                  }
                } catch (CommunicatorException e) {
                  e.printErrorMsg();
                }
              }
            })
        .start();
  }

  @Override
  protected void finalize() throws Throwable {
    closeAll();
    super.finalize();
  }

  private void closeAll() {
    try {
      if (out != null) out.close();
      if (in != null) in.close();
      if (socket != null) socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  class Receiver implements Runnable {

    Receiver() {
      new Thread(this, "Receiver").start();
    }

    public void run() {
      Message message;
      try {
        while ((message = Communicator.readFromChannel(Client.this.in)) != null) {
          System.out.println(message);
          inq.add(message);
        }
      } catch (CommunicatorException e) {
        e.printErrorMsg();
      } finally {
        Client.this.closeAll();
      }
    }
  }
}
