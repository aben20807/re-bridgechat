package test;

import java.util.Scanner;

import indi.aben20807.rebridgechat.bridge.Card;
import indi.aben20807.rebridgechat.bridge.Suits;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.connect.MessageOption;
import indi.aben20807.rebridgechat.connect.client.Client;

public class ClientTest {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {

		Client client = new Client();
		String s;
		do{
			s = scanner.nextLine();
			client.submitToServer(new Message(new Card('T', Suits.CLUBS, 10), MessageOption.CARD));
		}while(!s.equals("bye"));
	}
}