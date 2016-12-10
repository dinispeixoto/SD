import java.io.*;
import java.net.*;

public class Cliente{

	private static Menu menumain;
	private boolean logged=false;

	static public void main (String[] args){
		Socket c = null;		
		int numero;
		try{
			c = new Socket("localhost",8080); // Siga ligar-me ao Socket.
			System.out.println("Entrei no servidor"); // Vou mostrar que já entrei
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Vou ler do teclado
			BufferedReader ler_servidor = new BufferedReader(new InputStreamReader(c.getInputStream())); // Vou ler do Socket
			PrintWriter count = new PrintWriter(c.getOutputStream(),true);				// Vou escrever para o socket
			String m;
			//carregarMenus();
			//correMenus(br,count,ler_servidor);
			printMenu();
			while((m=br.readLine())!=null){
				if(m.equals("1")){
					try{
						count.println("1");
						System.out.print("Username: ");
						m = br.readLine();
						count.println(m);

						System.out.print("Password: ");
						m = br.readLine();
						count.println(m);

						m = ler_servidor.readLine();
						System.out.println("\n"+m+"\n");
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					printMenu();
				}
				else if(m.equals("2")){
					try{
						count.println("2");
						System.out.print("Username: ");
						m = br.readLine();
						count.println(m);

						System.out.print("Password: ");
						m = br.readLine();
						count.println(m);

						m = ler_servidor.readLine();
						System.out.println("\n"+m+"\n");
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					printMenu();
				}
				else if(m.equals("3")){
					try{
						count.println("3");
						System.out.print("Descrição do item a leiloar: ");
						m = br.readLine();
						count.println(m);

						m = ler_servidor.readLine();
						System.out.println("\n"+m+"\n");
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					printMenu();
				}
				else if(m.equals("4")){
					try{
						count.println("4");
						System.out.print("ID-Leilão: ");
						m = br.readLine();
						count.println(m);

						System.out.print("Valor: ");
						m = br.readLine();
						count.println(m);

						m = ler_servidor.readLine();
						System.out.println("\n"+m+"\n");
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					printMenu();
				}
				else if(m.equals("5")){
					try{
						count.println("5");
						m = ler_servidor.readLine();
						int tam = Integer.parseInt(m);
						System.out.println(tam);
						for(int i=0;i<tam;i++){
							m = ler_servidor.readLine();
							System.out.println("\n"+m+"\n");
						}
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
					printMenu();
				}
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	private static void printMenu(){
		System.out.print("1 - Registar como Comprador\n2 - Registar como Vendedor\n3 - Iniciar Leilão\n4 - Fazer Licitação\n5 - Consultar Leilões\n6 - Encerrar Leilão\n>");
	}

	/*
	private static void carregarMenus(){
        String[] ops = {"Registar Conta",
                        "Iniciar Sessão",
                        "Iniciar Leilão",
                        "Encerrar Leilão",
                    	"Consultar Leilões"};
        menumain = new Menu(ops);
    }
    
    private static void correMenus(BufferedReader br, PrintWriter count, BufferedReader ler_servidor){
        do {
           	menumain.executa("Eu");
            switch (menumain.getOpcao()) {
                  case 1: count.print("1");
                  		  registar(br,count,ler_servidor);
                          break;
                  //case 2: getConsultas();
                   //     break;
                  //case 3: setEstado();
                    //    break;
                  //case 4: 
             	}
             } while (menumain.getOpcao()!=0);
    }

	public static void registar(BufferedReader br, PrintWriter count, BufferedReader ler_servidor){
		try{
			String m;
			System.out.print("Username: ");
			m = br.readLine();
			count.println(m);

			System.out.print("Password: ");
			m = br.readLine();
			count.println(m);

			m = ler_servidor.readLine();
			System.out.print(m);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	*/
}
