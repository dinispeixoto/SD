import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ThreadServidorWrite extends Thread{
	private PrintWriter write_socket;
	private Condition c;
	private MensagemServidor ms;
	private ReentrantLock lock;

	public ThreadServidorWrite(PrintWriter write_socket, MensagemServidor ms){
		this.write_socket = write_socket;
		this.c = ms.getCondition();
		this.ms = ms;
		this.lock = ms.getLock();
	}
	
	public void run(){
		this.lock.lock();
		try{
			String linha;
			c.await();
			while((linha = ms.getMsg())!=null){
				if(linha.equals("Sair"))
					break;
				this.write_socket.println(linha);
				c.await();
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			this.lock.unlock();
		}
	}
}