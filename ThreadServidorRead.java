import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadServidorRead extends Thread{
	private BufferedReader read_socket;
	private GestorLeiloes g;
	private Utilizador u;

	public ThreadServidorRead(BufferedReader read_socket, GestorLeiloes g){
		this.read_socket = read_socket;
		this.g = g;
		this.u = null;
	}
	
	public void run(){
		try{
			String linha;
			while((linha = read_socket.readLine()) != null){ 
				if(input.equals("iniciar_sessao")){						
					String user,pass;
					user = read_socket.readLine();
					pass = read_socket.readLine();
					
					try{
						this.u = g.iniciar_sessao(user,pass);
					}
					catch(){
						//enivar para a outra thread
					}
				}
				else if(input.equals("registar_comprador")){					
					String user,pass;
					user = read_socket.readLine();
					pass = read_socket.readLine();

					try{
						g.registarUtilizador(user,pass,0);
					}
					catch(){
						//enivar para a outra thread
					}
				}
				else if(input.equals("registar_vendedor")){					
					String user,pass;
					user = read_socket.readLine();
					pass = read_socket.readLine();

					try{
						g.registarUtilizador(user,pass,1);
					}
					catch(){
						//enivar para a outra thread
					}
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}