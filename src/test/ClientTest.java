package test;

import java.util.Scanner;

import indi.aben20807.rebridgechat.bridge.Card;
import indi.aben20807.rebridgechat.bridge.Ranks;
import indi.aben20807.rebridgechat.bridge.Suits;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.connect.MessageOption;
import indi.aben20807.rebridgechat.connect.client.Client;

public class ClientTest {

  private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {

    Client client = Client.getInstance();
    String s;
    do {
      s = scanner.nextLine();
      switch (s) {
        case "chat":
          client.submitMessage(new Message(scanner.nextLine()));
          break;
        case "card":
          client.submitMessage(new Message(new Card(Suits.CLUBS, Ranks._T), MessageOption.CARD));
          break;
        case "get":
          System.out.println(client.getMessage());
          break;
        default:
          break;
      }
    } while (!s.equals("exit"));
  }
}
