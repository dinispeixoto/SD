/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdtrabalho;

/**
 *
 * @author Marcelo
 */
public class Leilao {
    private String descricao;
    private double licAtual;
    private Vendedor iniciador;
    
    public Leilao(){
        this("",0,null);
    }

    
    public Leilao(Leilao l){
        this.descricao = l.getDescricao();
        this.licAtual = l.getLicAtual();
        this.iniciador = l.getIniciador();
    }


    public Leilao(String descricao, double licAtual, Vendedor iniciador){
        this.descricao = descricao;
        this.licAtual = licAtual;
        this.iniciador = iniciador.clone();
    }
    
    public String getDescricao(){
        return this.descricao;
    }
    
    public double getLicAtual(){
        return this.licAtual;
    }
    
    public Vendedor getIniciador(){
        return this.iniciador.clone();
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
