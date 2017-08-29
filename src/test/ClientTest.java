package test;

import java.util.Scanner;

import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.connect.client.Client;

public class ClientTest {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {

		Client client = new Client();
		client.submitToServer(new Message("hello"));
		String s;
		do{
			s = scanner.nextLine();
			client.submitToServer(new Message(s));
		}while(!s.equals("bye"));
	}
}