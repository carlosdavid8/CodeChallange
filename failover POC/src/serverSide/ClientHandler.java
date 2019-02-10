package serverSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {
	final DataInputStream input;
	final DataOutputStream output;
	final Socket s;

	// Constructor
	public ClientHandler(Socket s, DataInputStream input, DataOutputStream output) {
		this.s = s;
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		String received;
		String toreturn;
		while (true) {
			try {

				// Ask user what he wants
				output.writeUTF("Please type one option as Request:\n" + "1) Say Hello\n" + "2) terminate connection.");

				received = input.readUTF();
				if (received.equals("2") || received.equals("checker")) {
					this.s.close();
					break;
				}

				// write on output stream based on the
				// answer from the client
				switch (received) {
				case "1":
					toreturn = "Hello Word!!!!!!";
					output.writeUTF(toreturn);
					break;

				default:
					output.writeUTF("Invalid input");
					break;
				}
			} catch (IOException e) {

				break;
			}

		}

		try {
			// closing resources
			this.input.close();
			this.output.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}