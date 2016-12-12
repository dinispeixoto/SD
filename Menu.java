import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    // variáveis de instância
    private String menu;
    private int op;
    
    /** Apresentar o menu */
    public void showMenu() {
        switch(op){
            case 0: System.out.println("1 - Iniciar Sessao\n2 - Registar como Comprador\n3 - Registar como Vendedor\n0 - Sair\n");
                    break;
            case 1: System.out.println("1 - Licitar um Leilao\n2 - Consultar Leiloes\n0 - Terminar Sessao\n");
                    break;
            case 2: System.out.println("1 - Iniciar Leilao\n2 - Consultar Leiloes\n3 - Encerrar Leilao\n0 - Terminar Sessao\n");
                    break;
        }
    }
    
    public int getOp(){
        return this.op;
    }

    public void setOp(int n){
        this.op=n;
    }

}
