package ua.mycompany.project.nextStep;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ServerSocketRunner implements Runnable {
	
	private static final Logger fileLogger = LogManager.getLogger(ServerSocketRunner.class.getName());
	private Thread mainThread;
	private AtomicBoolean needContinue = new AtomicBoolean(true);
	private  int PORT_NUMBER;
	private ExecutorService clientHandler;
	
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:tcp://127.0.0.1:9092/docker";
	static final String USER = ""; 
	static final String PASS = "";
	
	public ServerSocketRunner(int pORT_NUMBER) {
		super();
		PORT_NUMBER = pORT_NUMBER;
	}

	public void start() {
		mainThread = new Thread(this);
		mainThread.start();
		fileLogger.info("start main  thread ....");
	}

	@Override
	public void run() {
		try(Selector selector  = Selector.open(); ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
			String url = System.getenv("h2_db_url");
			fileLogger.info("found url " + url);
			
			
			
			config.setJdbcUrl(url);
	        config.setUsername(USER);
	        config.setPassword(PASS);
	        config.setDriverClassName(JDBC_DRIVER);
	        config.addDataSourceProperty("cachePrepStmts", "true");
	        config.addDataSourceProperty("prepStmtCacheSize", "250");
	        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	        
	        config.setConnectionTimeout(10000);
//	        config.setIdleTimeout(600000);
//	        config.setMaxLifetime(1800000);
	        ds = new HikariDataSource(config);
			
			
			InetSocketAddress address = new InetSocketAddress( PORT_NUMBER);
			serverSocket.bind(address);
			serverSocket.configureBlocking(false);
			int validOps = serverSocket.validOps();
			serverSocket.register(selector, validOps, null);
			fileLogger.info("server avaiting orders .......   ");
			while(needContinue.get()) {
				selector.select(30 * 1000);
				Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
				while(keyIterator.hasNext()) {
					SelectionKey currentKey = keyIterator.next();
					keyIterator.remove();
					if(currentKey.isAcceptable()) {
						SocketChannel clientChanal = serverSocket.accept();
						clientChanal.configureBlocking(false);
						clientChanal.register(selector, SelectionKey.OP_READ, null);
						fileLogger.info("server socket accept connection ");
					} else if(currentKey.isReadable()) {
						SocketChannel clientChanell = (SocketChannel) currentKey.channel();
						/// check if chanel  has more to  read  if clientChanell.read return not -1
						/// mb use executor for handling read operations
						ByteBuffer readBuffer = ByteBuffer.allocate(1024);
						
						clientChanell.read(readBuffer);
						
						byte[] b = new byte[readBuffer.position()];
						readBuffer.rewind();
						readBuffer.get(b);
						
						String message = IOUtils.toString(b, "utf-8");
						ClientInfo info = new ClientInfo();
						info.setMessage(message);
						
						handleIncomingMessage(message);
						fileLogger.info("income message " + message);
						info.setMessage(message);
						clientChanell.register(selector, SelectionKey.OP_WRITE, info);
						
						
					}else if(currentKey.isWritable()) {
						
						SocketChannel clientChanell = (SocketChannel) currentKey.channel();
						ClientInfo info = (ClientInfo) currentKey.attachment();
						String responce = "server receive message ==> " + info.getMessage();
						ByteBuffer writeBuffer = ByteBuffer.wrap(responce.getBytes(StandardCharsets.UTF_8));
						
						fileLogger.info("key atacment  " + info);
						clientChanell.write(writeBuffer);
						
						clientChanell.close();
						currentKey.cancel();
						
											
					}
				}
			}
			
			
		} catch (Exception e) {
			fileLogger.error(e);
		} 
	}
	
	private void handleIncomingMessage(String message) {
		Gson gson = new Gson();
		RequestEntity entity = gson.fromJson(message, RequestEntity.class);
		dbOperations(entity);
	}
	
	private void dbOperations(RequestEntity entity) {
		switch (entity.getComand().toLowerCase()) {
		case "stop": {
			needContinue.set(false);
			fileLogger.info("receive STOP comand  ");
			fileLogger.info("shut down server ........  ");
			break;
		}
		case "insert": {
			insertEntity(entity);
			break;
		}
		case "get": {
			getById(entity);
			break;
		}
		case "delete": {
			deleteById(entity);
			break;
		}
		default: {
			break;
		}
		}
		
	}
	
	private void insertEntity(RequestEntity entity) {
		String insert = " INSERT INTO TEST_DOCKER_1.TEST_1(name) VALUES (?)";
		try(Connection con = ds.getConnection(); PreparedStatement statment = con.prepareStatement(insert)){
			statment.setString(1, entity.getDbName());
			int row = statment.executeUpdate();
			fileLogger.info(" insert number of rows " + row);
		}catch (Exception e) {
			fileLogger.error(e);
		}
	}
	
	private String getById(RequestEntity entity) {
		String query = " SELECT id_entry, name FROM TEST_DOCKER_1.TEST_1 WHERE ID_entry = ? ";
		String name = "NOT FOUND";
		try(Connection con = ds.getConnection(); PreparedStatement statment = con.prepareStatement(query)){
			statment.setLong(1, entity.getId());
			ResultSet rSet = statment.executeQuery();
			if(rSet.next()) {
				name = rSet.getString("name");
			}
			fileLogger.info(" for id " + entity.getId() + " name " + name);
		}catch (Exception e) {
			fileLogger.error(e);
		}
		return name;
	}
	
	private void deleteById(RequestEntity entity) {
		String query = " DELETE FROM TEST_DOCKER_1.TEST_1 WHERE ID_entry = ? ";
		try(Connection con = ds.getConnection(); PreparedStatement statment = con.prepareStatement(query)){
			statment.setLong(1, entity.getId());
			int row = statment.executeUpdate();
			fileLogger.info(" delete from  db rows  " + row);
		}catch (Exception e) {
			fileLogger.error(e);
		}
		
	}

}
