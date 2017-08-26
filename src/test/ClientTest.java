package test;

import indi.aben20807.rebridgechat.connect.Message;
import indi.aben20807.rebridgechat.connect.client.Client;
import indi.aben20807.rebridgechat.exception.ClientException;

public class ClientTest {

	public static void main(String[] args) {

		Client client = new Client();
		try {
			client.submit(new Message("hello"));
		} catch (ClientException e) {
			e.printErrorMsg();
		}
	}
}