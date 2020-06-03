package ua.mycompany.project.serveremulator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class BufferTest {

	public static void main(String[] args) throws IOException {
						
		
//		RandomAccessFile f = new RandomAccessFile("D:\\garbage\\doc.txt", "rw");
//		ByteArrayInputStream input = new ByteArrayInputStream("qwerty".getBytes());
		
		
//		MappedByteBuffer mmb = f.getChannel().map(MapMode.READ_WRITE, 0, 5);
//		mmb.put("".getBytes());
//		
//		String str = f.readLine();
//		System.out.println(str);
//		
		
		
//		while(mmb.hasRemaining()) {
//			char chr = (char)mmb.get();
//			System.out.print(chr);
//		}	
		
		
		
//		FileChannel channel = f.getChannel();
//		CharBuffer cb = CharBuffer.allocate(3);
//		
		String testString = "hello kitty";
//		CharBuffer buffer = CharBuffer.allocate(100);
//		
//		buffer.append(testString);
//		
		ByteBuffer bb = ByteBuffer.allocate(4);
		
//		ReadableByteChannel ch1 = Channels.newChannel(new ByteArrayInputStream(testString.getBytes("utf-8")));
//		
//		while((ch1.read(bb))>0){
//	
//			bb.flip();
//			while(bb.hasRemaining()) {
//			char c = (char) bb.get();
//			
//			System.out.print(c);
//			}
//			bb.clear();
//		}
		
		
//		AsynchronousFileChannel afc = AsynchronousFileChannel.open(Paths.get("D:\\garbage\\doc.txt"), StandardOpenOption.READ);
//		afc.read(bb, 0, bb, new CompletionHandler<Integer, ByteBuffer>() {
//
//			@Override
//			public void completed(Integer result, ByteBuffer attachment) {
//				attachment.flip();
//				while(attachment.hasRemaining()) {
//					char ch = (char) attachment.get();
//					System.out.print(ch);
//					
//				}
//				
//			}
//
//			@Override
//			public void failed(Throwable exc, ByteBuffer attachment) {
//				System.out.println("FAIL !!!");
//				
//			}
//		});
		
		System.out.println();
		

	}

}
