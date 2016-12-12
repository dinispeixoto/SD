import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class Servidor{


	static public void main (String[] args){
		ServerSocket s;
		Socket c = null;
		int i=0;
		GestorLeiloes g = new GestorLeiloes();
		
		try{
			s = new ServerSocket(8080);
			System.out.println("Servidor inicializado!");
			BufferedReader read_socket = new BufferedReader(new InputStreamReader(c.getInputStream()));
			PrintWriter write_socket = new PrintWriter(c.getOutputStream(),true);

			while((c=s.accept()) != null){ 
				System.out.println("Um cliente ligou-se!"); 
				ThreadServidorRead tsr = new ThreadServidorRead(read_socket,g);
				ThreadServidorWrite tsw = new ThreadServidorWrite(write_socket);
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
