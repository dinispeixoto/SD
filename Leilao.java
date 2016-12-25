import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Leilao {
    private String descricao;
    private double licAtual;
    private String iniciador;
    private String topo;
    private Set<Comprador> licitadores;
    private ReentrantLock lock;
    
    public Leilao(){
        this("",null);
        this.licitadores = new HashSet<>();
        this.lock = new ReentrantLock(); 
        this.topo = null;
    }

    
    public Leilao(Leilao l){
        this.descricao = l.getDescricao();
        this.iniciador = l.getIniciador();
        this.licitadores = new HashSet<>();
        this.lock = new ReentrantLock(); 
        this.topo = null;
    }


    public Leilao(String descricao,Utilizador iniciador){
        this.descricao = descricao;
        this.iniciador = iniciador.getUsername();
        this.licitadores = new HashSet<>();
        this.lock = new ReentrantLock(); 
        this.topo = null;
    }
    
    public String getDescricao(){
        return this.descricao;
    }
    
    public double getLicAtual(){
        this.lock.lock();
        try{
            return this.licAtual;
        }
        finally{
            this.lock.unlock();
        }
    }
    
    public String getIniciador(){
        return this.iniciador;
    }
    
    public String getVencedor(){
        this.lock.lock();
        try{
            return this.topo;
        }
        finally{
            this.lock.unlock();
        }
    }
    
    public Set<Comprador> getLicitadores(){
        this.lock.lock();
        try{
            return this.licitadores;
        }
        finally{
            this.lock.unlock();
        }
    }
    
    public void licitar(Comprador c,double lic){
        this.lock.lock();
        try{   
            this.licAtual = lic;
            this.topo = c.getUsername();
            licitadores.add(c);
        }
        finally{
            this.lock.unlock();
        }
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
