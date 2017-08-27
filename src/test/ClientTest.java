package test;

import java.util.Scanner;

import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.connect.client.Client;
import indi.aben20807.rebridgechat.exception.ClientException;

public class ClientTest {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {

		Client client = new Client();
		
		try {
			String s;
			do{
				s = scanner.nextLine();
				client.submit(new Message(s));
			}while(!s.equals("bye"));
		} catch (ClientException e) {
			e.printErrorMsg();
		}
	}
}