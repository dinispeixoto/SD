import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadServidorRead extends Thread{
	private BufferedReader read_socket;
	private GestorLeiloes g;
	private Utilizador u;
	private MensagemServidor ms;

	public ThreadServidorRead(BufferedReader read_socket, GestorLeiloes g, MensagemServidor ms){
		this.read_socket = read_socket;
		this.g = g;
		this.u = null;
		this.ms = ms;
	}
	
	public void run(){
		try{
			String input;
			while((input = read_socket.readLine()) != null){ 
				if(input.equals("iniciar_sessao")){						
					String user,pass;
					user = read_socket.readLine();
					pass = read_socket.readLine();
					
					try{
						this.u = g.iniciarSessao(user,pass);
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("registar_comprador")){					
					String user,pass;
					user = read_socket.readLine();
					pass = read_socket.readLine();

					try{
						g.registarUtilizador(user,pass,0);
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("registar_vendedor")){					
					String user,pass;
					user = read_socket.readLine();
					pass = read_socket.readLine();

					try{
						g.registarUtilizador(user,pass,1);
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("licitar")){					

					try{
						input=null;
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("iniciar_leilao")){					
					
					try{
						input=null;	
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("consultar")){					

					try{
						input=null;
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("eliminar_leilao")){					
					
					try{
						input=null;
					}
					catch(Exception e){
						ms.setMsg(e.getMessage());
					}
				}
				else if(input.equals("terminar_sessao")){					
					this.u = null;
					ms.setMsg("terminou sessao");
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}