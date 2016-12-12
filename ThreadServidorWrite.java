import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadServidorWrite extends Thread{
	private BufferedReader write_socket;
	private Condition c;
	private Utilizador u;

	public ThreadServidorWrite(BufferedReader write_socket,Condition c){
		this.write_socket = write_socket;
		this.g = g;
	}
	
	public void run(){
		
		try{
			while(true){
				this.write_socket.println("");
				c.await();
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}