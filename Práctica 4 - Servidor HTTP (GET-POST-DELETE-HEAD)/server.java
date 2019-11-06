import java.net.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
	public static int port = 8888;

	public static void main(String args[]) throws IOException, InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(3);
		Scanner sc = new Scanner(System.in);
		System.out.print("Puerto: ");
		port = sc.nextInt();
		ServerSocket server = new ServerSocket(port);
		System.out.println("Escuchando por " + port + " ...");
		while(true){
			try{
				Socket client = server.accept();
				System.out.println("Nuevo cliente conectado desde " + client.getInetAddress() + ":" + client.getPort());
				clientHandler handler = new clientHandler(client);
				pool.execute(handler);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}