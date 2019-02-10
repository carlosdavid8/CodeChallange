package serverSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import util.PropertiesHelper;

public class FailoverChecker extends Thread {

	@Override
	public void run() {
		String host = null;
		int port = 0;
		int backup = 0;
		Socket s = new Socket();
		try {
			host = PropertiesHelper.getHost();
			port = PropertiesHelper.getPort();
			backup = PropertiesHelper.getBackup();
			s.connect(new InetSocketAddress(host, port));
			DataOutputStream output = new DataOutputStream(s.getOutputStream());
			DataInputStream input = new DataInputStream(s.getInputStream());
			System.out.println("Connected to Primary service: " + s);
			System.out.println("Service up:" + !input.readUTF().isEmpty());
			output.writeUTF("checker");
			s.close();
			input.close();
			output.close();

		}

		catch (Exception e) {

			System.out.println("Service up:False");
			System.out.println("Failover Mechanism starting.....");

			PropertiesHelper.failoverSwitch(String.valueOf(port), String.valueOf(backup), host);

			Server server = new Server();
			try {
				server.upServer();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		while (true) {
			Thread t = new FailoverChecker();
			t.start();
			Thread.sleep(30000);
		}
	}

}
