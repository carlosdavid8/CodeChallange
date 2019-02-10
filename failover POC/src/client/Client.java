package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import util.PropertiesHelper;

public class Client {
	public static void main(String[] args) throws IOException {
		try {
			Scanner scn = new Scanner(System.in);
			String host = PropertiesHelper.getHost();
			int port = PropertiesHelper.getPort();
			// getting localhost ip
			InetAddress ip = InetAddress.getByName(host);
			// establish the connection with server port
			Socket s = new Socket(ip, port);

			// obtaining input and out streams
			DataInputStream input = new DataInputStream(s.getInputStream());
			DataOutputStream output = new DataOutputStream(s.getOutputStream());

			// the following loop performs the exchange of
			// information between client and client handler
			while (true) {
				System.out.println(input.readUTF());
				String tosend = scn.nextLine();
				output.writeUTF(tosend);

				// If client sends exit,close this connection
				// and then break from the while loop
				if (tosend.equals("Exit")) {
					System.out.println("Closing this connection : " + s);
					s.close();
					System.out.println("Connection closed");
					break;
				}

				// printing information requested by client
				String received = input.readUTF();
				System.out.println(received);
			}

			// closing resources
			scn.close();
			input.close();
			output.close();
		} catch (Exception e) {
			System.out.println("El service is not reachable");

		}
	}
}