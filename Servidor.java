import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class Servidor{


	static public void main (String[] args){
		ServerSocket s;
		Socket c = null;
		int i=0;
		GestorLeiloes g = new GestorLeiloes();
		ReentrantLock lock = new ReentrantLock();
		Condition cond = lock.newCondition();

		
		try{
			s = new ServerSocket(8080);
			System.out.println("Servidor inicializado!");
			
			while((c=s.accept()) != null){ 
				System.out.println("Um cliente ligou-se!"); 
				BufferedReader read_socket = new BufferedReader(new InputStreamReader(c.getInputStream()));
				PrintWriter write_socket = new PrintWriter(c.getOutputStream(),true);
				MensagemServidor ms = new MensagemServidor(cond,lock);
				ThreadServidorRead tsr = new ThreadServidorRead(read_socket,g,ms);
				ThreadServidorWrite tsw = new ThreadServidorWrite(write_socket,cond,ms,lock);
				tsr.start();
				tsw.start();
			}
			s.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage()); 
		}
	}
}
