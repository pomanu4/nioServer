package ua.mycompany.project.serveremulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ua.mycompany.project.nextStep.MainTest;
import ua.mycompany.project.nextStep.RequestEntity;

public class MainClass {
	
	private static final Logger mLoggerX = LogManager.getLogger("mainLogger");
		
	public static void main(String... args) {
		
		try {
			MainClass mt = new MainClass();
			
			RequestEntity insert = new RequestEntity("insert", 0, "new_db_entity_name");
			RequestEntity getById = new RequestEntity("get", 4, "");
			RequestEntity delete = new RequestEntity("delete", 4, "");
			RequestEntity stop = new RequestEntity("stop", 4, "");
			
			String request = mt.getRequest(stop);
			mLoggerX.info(request);
			
			
			
			
//			ServerSocket serverSocket = new ServerSocket(35999);
//			ExecutorService service = Executors.newFixedThreadPool(3);
//			while (true) {
//				Socket client = serverSocket.accept();
//				System.out.println("connection accepted ");
//				ThredWorker worker = new ThredWorker(client, UUID.randomUUID().toString());
//				service.execute(worker);
//
//			}
			
//			/*
				InetSocketAddress address = new InetSocketAddress("127.0.0.1", 29888);
//				InetSocketAddress address = new InetSocketAddress(29888);
				SocketChannel channel = SocketChannel.open(address);
			
				ByteBuffer buffer = ByteBuffer.allocate(1024);
//				buffer.put("stop".getBytes(StandardCharsets.UTF_8));
				buffer.put(request.getBytes(StandardCharsets.UTF_8));
				buffer.flip();
				channel.write(buffer);
				
				buffer.clear();
				channel.read(buffer);
				mLoggerX.info(new String(buffer.array()));
				
				channel.close();
			
	//		*/
			
		} catch (Exception e) {
			mLoggerX.error(e);
		}

	}
	
	private String getRequest(RequestEntity entity) {
		Gson gson = new Gson();
		
		
		
		return gson.toJson(entity);
	}
	
}
