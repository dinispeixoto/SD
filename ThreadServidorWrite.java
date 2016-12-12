import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ThreadServidorWrite extends Thread{
	private PrintWriter write_socket;
	private Condition c;
	private MensagemServidor ms;

	public ThreadServidorWrite(PrintWriter write_socket, Condition c, MensagemServidor ms){
		this.write_socket = write_socket;
		this.c = c;
		this.ms = ms;
	}
	
	public void run(){
		
		try{
			String linha;
			while((linha = ms.getMsg())!=null){
				this.write_socket.println(linha);
				c.await();
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}