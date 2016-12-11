/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Marcelo
 */
public class GestorLeiloes {
    private Map<String,Leilao> leiloes;
    private Set<Utilizador> utilizadores;
    private int idLeilao=0;
    
    
    public GestorLeiloes(){
        this.leiloes = new HashMap<>();
        this.utilizadores = new HashSet<>();
    }
    
    public void registarUtilizador(Utilizador u)throws UsernameInvalidoException{
        if(utilizadores.stream().anyMatch(a -> a.getUsername().equals(u.getUsername())))
                    {throw new UsernameInvalidoException("Username já se encontra em uso!");}
        else {utilizadores.add(u);}
    }
    
    public String adicionarLeilao(Leilao l,Utilizador u) throws SemAutorizacaoException{
        if(u.getClass().getName().equals("Comprador"))
                    {throw new SemAutorizacaoException("Necessita de ser um Vendedor para iniciar um Leilão!");}
        else { Leilao lei = l.clone();
               String id = Integer.toString(idLeilao);
               leiloes.put(id,l);
               System.out.println("coloquei leilao");
               idLeilao++;
               //((Vendedor)u).addLeilao(l);
        }
        return Integer.toString(idLeilao-1);
    }
    
    public void licitarLeilao(String idLeilao,Utilizador u,double lic) throws SemAutorizacaoException, LeilaoInexistenteException, LicitacaoInvalidaException{
        if(u.getClass().getName().equals("Vendedor"))
                    {throw new SemAutorizacaoException("Necessita de ser um Comprador para fazer uma licitação!");}
        else if(!(leiloes.containsKey(idLeilao)))
                    {throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");}
        else if(leiloes.get(idLeilao).getLicAtual() > lic)
                    {throw new LicitacaoInvalidaException("Necessita de ser um Vendedor para iniciar um Leilão!");}
        else{leiloes.get(idLeilao).licitar((Comprador)u, lic);}
    }
    
    public String encerrarLeilao(String idLeilao,Utilizador u) throws SemAutorizacaoException, LeilaoInexistenteException{
        if(u.getClass().getName().equals("Comprador"))
                    {throw new SemAutorizacaoException("Necessita de ser um Vendedor para encerrar um leilão!");}
        else if(!(leiloes.containsKey(idLeilao)))
                    {throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");}
        else if(!(leiloes.get(idLeilao).getIniciador().equals(u)))
                    {throw new SemAutorizacaoException("Necessita de ser o iniciador de um leilão para o poder encerrar!");}
        else{
            double lic = leiloes.get(idLeilao).getLicAtual();
            String venc = leiloes.get(idLeilao).getVencedor();
            return("O leilao "+idLeilao+" foi encerrado com o valor de "+lic+"€, ganho por "+venc+"!");}
    }

    public String[] consultarLeiloes(Utilizador u){
        String[] string = new String[(leiloes.size())];
        int i=0;
        for(Map.Entry<String,Leilao> entry : leiloes.entrySet()){
            Leilao x = entry.getValue();
            String a = entry.getKey();
            if(x.getIniciador().equals(u)){
                string[i] = "* "+"ID-Leilão = "+a+" - Descrição: "+x.getDescricao();
            }
            else if(x.getVencedor().equals(u.getUsername())){
                string[i] = "+ "+"ID-Leilão = "+a+" - Descrição: "+x.getDescricao();
            }
            else{
                string[i] = "ID-Leilão = "+a+" - Descrição: "+x.getDescricao();
            }
            i++;
        }
        return string;
    }
}
