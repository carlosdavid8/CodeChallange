package serverSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util.PropertiesHelper;

public class Server {

	// Constructor
	public Server() {

	}

	public void upServer() throws IOException {
		ServerSocket Primary = new ServerSocket(PropertiesHelper.getPort());
		System.out.println("Server Starting");
		// running infinite loop for getting
		// client request
		while (true) {
			Socket s = null;

			try {
				// socket object to receive incoming client requests
				s = Primary.accept();

				System.out.println("A new client is connected : " + s);

				// obtaining input and out streams
				DataInputStream input = new DataInputStream(s.getInputStream());
				DataOutputStream output = new DataOutputStream(s.getOutputStream());

				System.out.println("Assigning new thread for this client");

				// create a new thread object
				Thread t = new ClientHandler(s, input, output);

				// Invoking the start() method
				t.start();

			} catch (Exception e) {
				s.close();
				e.printStackTrace();
				System.out.println("Server down");
			}

		}

	}

	public static void main(String[] args) throws IOException {
		Server s = new Server();
		s.upServer();

	}
}