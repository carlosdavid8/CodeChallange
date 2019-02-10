package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesHelper {

	public static String getHost() throws IOException {
		String value = null;
		Properties pro = new Properties();
		FileInputStream fis;

		fis = new FileInputStream("config.properties");
		pro.load(fis);
		fis.close();
		value = pro.getProperty("host");
		return value;
	}

	public static int getPort() throws IOException {
		String value = null;
		Properties pro = new Properties();
		FileInputStream fis;

		fis = new FileInputStream("config.properties");
		pro.load(fis);
		fis.close();
		value = pro.getProperty("port");
		return Integer.valueOf(value);
	}

	public static int getBackup() throws IOException {
		String value = null;
		Properties pro = new Properties();
		FileInputStream fis;

		fis = new FileInputStream("config.properties");
		pro.load(fis);
		fis.close();
		value = pro.getProperty("backup");
		return Integer.valueOf(value);
	}

	public static void failoverSwitch(String primary, String backup, String host) {
		Properties prop = new Properties();
		OutputStream output = null;
		try {

			output = new FileOutputStream("config.properties");

			// set the properties value
			prop.setProperty("port", backup);
			prop.setProperty("backup", primary);
			prop.setProperty("host", host);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
