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

			MyThread mt[] = new MyThread[10];

			while((c=s.accept()) != null && i<10){ 
				System.out.println("Um cliente ligou-se!"); 
				mt[i] = new MyThread(c,g);
				mt[i].start();
				i++;
			}

			s.close();
			for(int j = 0; j<10; j++){
				mt[j].join();
			}

		}
		catch(IOException e){
			System.out.println(e.getMessage()); // Se der asneira
		}
		catch(InterruptedException e){
			System.out.println("Erro AQUI: " + e.getMessage()); // Se der asneira
		}
	}
}
