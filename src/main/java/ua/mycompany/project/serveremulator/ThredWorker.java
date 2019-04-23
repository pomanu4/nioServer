package ua.mycompany.project.serveremulator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ThredWorker implements Runnable{
    
    private final Socket socket;
    private final String name;

    public ThredWorker(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        try{
            System.out.println(" new thread "+name);
           String message = "hello world -> " + this.name;
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                writer.print("HTTP/1.1 200 OK\n");
                writer.print("Transfer-Encoding: chunked\n");
                writer.print("Server: LiteSpeed\n");
                writer.print("Content-Type: text/html; charset=UTF-8\n");
                writer.print("Cache-control: private, max-age=0, no-cache\n");
                writer.print("Connection: close\n");
                
                writer.print("\r\n");
                writer.print(message);
                writer.flush();
            }
            socket.close();
           System.out.println(" new thread ends " + name);
        } catch (IOException ex) {
            Logger.getLogger(ThredWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
