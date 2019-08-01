package ua.mycompany.project.serveremulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class NioSocetOperations {

    public static void main(String... args) throws IOException {
        /*
        Path filePath = Paths.get("D:\\garbage\\html", "index.html");
        File file = filePath.toFile();
        try (FileChannel fch = (FileChannel) Channels.newChannel(new FileInputStream(file))) {
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            StringBuilder strBuff = new StringBuilder();
            int read =0;
            while((read = fch.read(buffer)) != -1){
                strBuff.append(new String(buffer.array()));
                buffer.rewind();
            } 
            
            String s = strBuff.toString();
            System.out.println(s);
        }
         */
        
        Selector selector = Selector.open();
        ServerSocketChannel serverSocChanel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(35999);
        serverSocChanel.bind(address);
        serverSocChanel.configureBlocking(false);
        int num = serverSocChanel.validOps();//return only one valid option OP_Accept (16)
        serverSocChanel.register(selector, num, null);
        while (true) {
            System.out.println("server are waiting for connection ");
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey actionKey = iterator.next();               
                if (actionKey.isAcceptable()) {
                    SocketChannel clientChannel = serverSocChanel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    
                    System.out.println("connection is accepted " + clientChannel.getLocalAddress().toString());
                } else if (actionKey.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) actionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    clientChannel.read(buffer);
                    String request = new String(buffer.array()).trim();
                    System.out.println("browser request is : \n\n" + request);
                    clientChannel.register(selector, SelectionKey.OP_WRITE);
                    
                    
//                    actionKey.cancel();
                } else if (actionKey.isWritable()) {
                    SocketChannel clientChannel = (SocketChannel) actionKey.channel();
                    ByteBuffer responce = ByteBuffer.wrap(createHeaders().getBytes());
                    clientChannel.write(responce);
                    
//                    Path path = Paths.get("./src/main/java/resource/test.txt");
//                    byte[] cont = Files.readAllBytes(path);
//                    clientChannel.write(ByteBuffer.wrap(cont));
                    
//                    actionKey.cancel();
                    clientChannel.close();
                    System.out.println("connection is closed");
                }
                iterator.remove();
            }
        }

    }

    private static String createHeaders() {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 200 ok \r\n")
//               .append("HTTP/1.1 308 redirect \r\n")
               .append("Connection: close \r\n")
               .append("Content-Type: text/html; charset=utf-8 \r\n")
//               .append("Content-Type: application/octet-stream \r\n")
//               .append("Content-Disposition: attachment; filename=test.txt")
               .append("\r\n")
               .append("<!DOCTYPE html><html><body><h1>hello world</h1><br><p>nio server response</p></body></html>");
        String response = builder.toString();

        return response;
    }
}
