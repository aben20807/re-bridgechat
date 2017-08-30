package test;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import indi.aben20807.rebridgechat.bridge.Card;
import indi.aben20807.rebridgechat.bridge.Suits;
import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.connect.MessageOption;
import indi.aben20807.rebridgechat.connect.client.Client;

public class ClientTest {

	private static Scanner scanner = new Scanner(System.in);
	final static ExecutorService executorService = Executors.newCachedThreadPool();
	
	public static void main(String[] args) {

		Client client = new Client();
		String s;
		do{
			s = scanner.nextLine();
			switch (s) {
				case "chat":
					client.submitToServer(new Message(scanner.nextLine()));
					break;
				case "card":
					client.submitToServer(new Message(new Card('T', Suits.CLUBS, 10), MessageOption.CARD));
					break;
				default:
					break;
			}
		}while(!s.equals("exit"));
	}
}