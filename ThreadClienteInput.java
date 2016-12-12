import java.io.*;
import java.net.*;

public class ThreadClienteInput extends Thread{
	
	private BufferedReader ler_teclado;
	private PrintWriter escrever_socket;
	private Menu menu;

	public ThreadClienteInput(BufferedReader b, PrintWriter p, Menu menu){
		this.ler_teclado = b;
		this.escrever_socket = p;
		this.menu = menu;
	}

	public void run(){
		
		String input = null;

		try{
			while((input = ler_teclado.readLine())!= null){
				if(menu.getOp() == 0){							// Não tem sessão iniciada
					if(input.equals("1")){						// Iniciar sessão
						escrever_socket.println("iniciar_sessao");
						System.out.println("Username: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.println("Password: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

					}
					else if(input.equals("2")){					// Registar como comprador
						escrever_socket.println("registar_comprador");
						System.out.println("Username: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.println("Password: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
					}
					else if(input.equals("3")){					// Registar como vendedor
						escrever_socket.println("registar_vendedor");
						System.out.println("Username: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.println("Password: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
					}
					else if(input.equals("0")){					// Sair
						break;
					}
				}

				else if(menu.getOp() == 1){						// Comprador logado.
					if(input.equals("1")){						// Licitar 
						escrever_socket.println("licitar");
					}
					else if(input.equals("2")){					// Consultar leilão
						escrever_socket.println("consultar_leilao");
					}
					else if(input.equals("0")){					// Terminar sessão
						break;
					}
				}

				else if(menu.getOp() == 2){						// Vendedor logado.
					if(input.equals("1")){						// Iniciar leilão
						escrever_socket.println("iniciar_leilao");
						System.out.println("Descrição: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
					}
					else if(input.equals("2")){					// Consultar
						escrever_socket.println("consultar_leilao");
					}
					else if(input.equals("3")){					// Encerrar leilão
						escrever_socket.println("encerrar_leilao");
					}
					else if(input.equals("0")){					// Terminar sessão
						break;
					}			
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
}