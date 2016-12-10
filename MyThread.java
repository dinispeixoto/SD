import java.lang.Thread;
import java.io.*;
import java.net.*;

public class MyThread extends Thread{
	private Socket c;
	private GestorLeiloes g;
	private Utilizador u;

	public MyThread(Socket c,GestorLeiloes g){
		this.c = c;
		this.g = g;
	}
	
	public void run(){
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream())); // Reader do Socket
			PrintWriter count = new PrintWriter(c.getOutputStream(),true); // Writer para o Socket
			String m;

			while((m=br.readLine()) != null){ // Enquanto há para ler
				if(m.equals("1")) {
					try{
						String user = null;
						String pass = null;
						user = br.readLine();
						pass = br.readLine();
						u = new Comprador(user,pass," ",null);
						try{
							g.registarUtilizador(u);
							count.println("-> Registo efetuado com sucesso!");
							System.out.println("Cliente registado!");
						}
						catch(Exception e){
							count.println(e.getMessage());
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				if(m.equals("2")) {
					try{
						String user = null;
						String pass = null;
						user = br.readLine();
						pass = br.readLine();
						u = new Vendedor(user,pass," ",null);
						try{
							g.registarUtilizador(u);
							count.println("-> Registo efetuado com sucesso!");
							System.out.println("Cliente registado!");
						}
						catch(Exception e){
							count.println(e.getMessage());
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				if(m.equals("3")) {
					try{
						String descricao = null;
						descricao = br.readLine();
						Leilao l = new Leilao(descricao,u);
						try{
							String a = g.adicionarLeilao(l,u);
							count.println("-> Leilão adicionado com sucesso - ID do leilão: "+a);
							System.out.println("Leilão adicionado!");
						}
						catch(Exception e){
							count.println(e.getMessage());
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				if(m.equals("4")) {
					try{
						String idLeilao = null;
						String valor = null;
						idLeilao = br.readLine();
						valor = br.readLine();
						double val = Double.parseDouble(valor); 
						try{
							g.licitarLeilao(idLeilao,u,val);
							count.println("-> Licitação efetuada com sucesso!");
							System.out.println("Utilizador acavou de fazer uma licitação!");
						}
						catch(Exception e){
							count.println(e.getMessage());
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				if(m.equals("5")) {
					try{
						System.out.println("ola");
						String[] cons = g.consultarLeiloes(u);
						System.out.println("ola");
						count.println(cons.length);
						for(int i=0;i<cons.length;i++){
							System.out.println(cons[i]);
							count.println(cons[i]);
						}
					}	
					catch(Exception e){
						count.println(e.getMessage());
					}
				}
			}
			c.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void registar(BufferedReader br,PrintWriter count){
		try{
			String user = null;
			String pass = null;
			System.out.println("oi");
			user = br.readLine();
			System.out.println("oi");
			pass = br.readLine();
			System.out.println("oi");
			Comprador u = new Comprador(user,pass," ",null);
			try{g.registarUtilizador(u);}
			catch(Exception e){count.print(e.getMessage());}
			count.println("ok");
			System.out.println("passei");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
