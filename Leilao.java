import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.util.ArrayList;

public class Leilao {
    private String descricao;
    private double licAtual;
    private Utilizador iniciador;
    private ArrayList<Comprador> licitadores;
    
    public Leilao(){
        this("",null);
        this.licitadores = new ArrayList<>();
    }

    
    public Leilao(Leilao l){
        this.descricao = l.getDescricao();
        this.iniciador = l.getIniciador();
        this.licitadores = new ArrayList<>();
    }


    public Leilao(String descricao,Utilizador iniciador){
        this.descricao = descricao;
        this.iniciador = iniciador.clone();
        this.licitadores = new ArrayList<>();
    }
    
    public synchronized String getDescricao(){
        return this.descricao;
    }
    
    public synchronized double getLicAtual(){
        return this.licAtual;
    }
    
    public synchronized Utilizador getIniciador(){
        return this.iniciador.clone();
    }
    
    public synchronized String getVencedor(){
        return licitadores.get(licitadores.size()-1).getUsername();
    }
    
    public synchronized ArrayList<Comprador> getLicitadores(){
        return this.licitadores;
    }
    
    public synchronized void licitar(Comprador c,double lic){
        this.licAtual = lic;
        licitadores.add(c);
    }

    public Leilao clone(){
        return new Leilao(this);
    }

   public boolean equals(Object obj){
      if(this == obj)
        return true;
      if ((obj==null) || (this.getClass() != obj.getClass()))
        return false;
      Leilao l = (Leilao) obj;
        return (this.descricao.equals(l.getDescricao()) && 
                this.licAtual==l.getLicAtual() &&
                this.iniciador.equals(l.getIniciador()));
   }
    
}
