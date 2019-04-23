package ua.mycompany.project.serveremulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

//    public static void main(String... args) throws IOException {
//
//        ServerSocket serverSocket = new ServerSocket(8080);
//        ExecutorService service = Executors.newFixedThreadPool(3);
//        while(true){
//            Socket client = serverSocket.accept();
//            System.out.println("connection accepted ");
//            ThredWorker worker = new ThredWorker(client, UUID.randomUUID().toString());
//            service.execute(worker);
//      
//        
//             System.out.println("waiting for next client");  
//        }
//    }
}
