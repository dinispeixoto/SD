import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadClienteOutput extends Thread{
	private BufferedReader ler_socket;
	private Menu menu;

	public ThreadClienteOutput(BufferedReader ler_socket, Menu menu){
		this.ler_socket = ler_socket;
		this.menu = menu;
	}
	
	public void run(){
		try{
			String linha;								
			while((linha = ler_socket.readLine())!=null){
				if(linha.equals("Iniciou sessão como Comprador!")){
					menu.setOp(1);
				}
				else if(linha.equals("Iniciou sessão como Vendedor!")){
					menu.setOp(2);
				}
				else if(linha.equals("Terminou sessão")){
					menu.setOp(0);
				}

				System.out.println(linha);

			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}