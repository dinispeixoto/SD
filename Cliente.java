import java.io.*;
import java.net.*;

public class Cliente{

	public static void main(String[] args){

		String input = null;
		Utilizador utilizador = null;
		Socket socket = null;

		try{

			socket = new Socket("localhost",8080);
			BufferedReader ler_teclado = new BufferedReader(new InputStreamReader(System.in)); 
			BufferedReader ler_socket = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			PrintWriter escrever_socket = new PrintWriter(socket.getOutputStream(),true);	

			Menu menu = new Menu();
			ThreadClienteInput tci = new ThreadClienteInput(ler_teclado,escrever_socket,menu);
			ThreadClienteOutput tco = new ThreadClienteOutput(ler_socket,menu);

			tci.start();	
			tco.start();

			tci.join();
			tco.join();

			ler_teclado.close();
			ler_socket.close();
			escrever_socket.close();

			System.out.println("Adeus!\n");
			socket.close();

		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}   
		catch(InterruptedException e){
			System.out.println(e.getMessage());
		}
	}
}