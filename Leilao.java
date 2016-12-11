/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Marcelo
 */
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
    
    public String getDescricao(){
        return this.descricao;
    }
    
    public double getLicAtual(){
        return this.licAtual;
    }
    
    public Utilizador getIniciador(){
        return this.iniciador.clone();
    }
    
    public String getVencedor(){
        String s = "Não existem licitadores!";
        if(licitadores.size()!=0){
            s = licitadores.get((licitadores.size())-1).getUsername();
        }
        return s;
    }
    
    public ArrayList<Comprador> getLicitadores(){
        return this.licitadores;
    }
    
    public void licitar(Comprador c,double lic){
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
