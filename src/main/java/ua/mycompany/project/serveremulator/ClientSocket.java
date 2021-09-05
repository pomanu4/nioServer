package ua.mycompany.project.serveremulator;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class ClientSocket {

	public static void main(String[] args) {
		try(Socket socket = new Socket("127.0.0.1", 35999);
				InputStream inputStream = socket.getInputStream()) {
			
			
				String string = IOUtils.toString(inputStream);
				System.out.println(string);
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
