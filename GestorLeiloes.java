import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.util.List;
import java.util.ArrayList;


public class GestorLeiloes {
    /*Map com os diversos leiloes activos*/
    private Map<String,Leilao> leiloes;
    /*Map com todos os utilizadores registados*/
    private Map<String,Utilizador> utilizadores;
    /*Map com todas as caixas de mensagem de cada utilizador*/
    private Map<String,MensagemServidor> mensagens;
    /*ID inicial do leilao*/
    private int idLeilao=1;
    /*Locks para tratar da concorrencia*/
    private ReentrantLock leiloesLock;
    private ReentrantLock mensagensLock;
    private ReentrantLock registosLock;
    
    public GestorLeiloes(){
        this.leiloes = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.mensagens = new HashMap<>();
        this.leiloesLock = new ReentrantLock();
        this.mensagensLock = new ReentrantLock();
        this.registosLock = new ReentrantLock();
    }
    
    public Utilizador iniciarSessao(String username, String password, MensagemServidor ms) throws UsernameInexistenteException, PasswordIncorretaException{
        this.registosLock.lock();
        try{
            if(!this.utilizadores.containsKey(username)){
                throw new UsernameInexistenteException("Username inexistente!");
            }
            else{
                if(!this.utilizadores.get(username).getPassword().equals(password)){
                    throw new PasswordIncorretaException("A password está incorreta!");
                }
                else{
                    if(this.mensagens.containsKey(username)){
                        MensagemServidor m = this.mensagens.get(username);
                        String linha;
                        while((linha = m.getMsg())!=null){
                            ms.setMsg(linha,null);
                        }
                        this.mensagens.put(username,ms);
                    }
                    return this.utilizadores.get(username);
                }
            }
        }
        finally{
            this.registosLock.unlock();
        }
    }
    
    public void registarUtilizador(String user, String pass, int op, MensagemServidor ms) throws UsernameInvalidoException{
        this.registosLock.lock();
        try{
            if(this.utilizadores.containsKey(user)){
                throw new UsernameInvalidoException("Username já se encontra em uso!");
            }
            else {
                switch(op){
                    case 0: Comprador c = new Comprador (user,pass,null);
                        this.utilizadores.put(user,c);
                        this.mensagens.put(user,ms);
                        break;
                    case 1: Vendedor v = new Vendedor (user,pass,null);
                        this.utilizadores.put(user,v);
                        this.mensagens.put(user,ms);
                        break;
                }
            } 
        }
        finally{
            this.registosLock.unlock();
        }
    }
    
    public String adicionarLeilao(Leilao l,Utilizador u) throws SemAutorizacaoException{
        if(u.getClass().getName().equals("Comprador")){
            throw new SemAutorizacaoException("Necessita de ser um Vendedor para iniciar um Leilão!");
        }
        this.leiloesLock.lock();
        try{
            Leilao lei = l.clone();
            String id = Integer.toString(this.idLeilao);
            leiloes.put(id,l);
            this.idLeilao++;
        
            return Integer.toString(idLeilao-1);
        }
        finally{
            this.leiloesLock.unlock();
        }
    }
    
    public void licitarLeilao(String idLeilao,Utilizador u,double lic) throws SemAutorizacaoException, LeilaoInexistenteException, LicitacaoInvalidaException{
        if(u.getClass().getName().equals("Vendedor")){
                throw new SemAutorizacaoException("Necessita de ser um Comprador para fazer uma licitação!");
        }
        this.leiloesLock.lock();
        try{
            if(!(leiloes.containsKey(idLeilao))){
                throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");
            }
            else if(leiloes.get(idLeilao).getLicAtual() > lic){
                throw new LicitacaoInvalidaException("Licitação com valor inferior a última licitação!");
            }
            else if(leiloes.get(idLeilao).getLicAtual() == lic){
                throw new LicitacaoInvalidaException("Licitação com valor igual a última licitação!");
            }
            else{
           
                Leilao l = this.leiloes.get(idLeilao);
        
                String topo = l.getVencedor();
            
                l.licitar((Comprador)u, lic);
            
                if(topo != null)
                    this.mensagens.get(topo).setMsg("No leilao "+idLeilao+" foi ultrapassado pelo "+u.getUsername()+", com o valor de "+lic+"€",null);
            }
        }  
        finally{
            this.leiloesLock.unlock();
        }
    }
    
    public String encerrarLeilao(String idLeilao,Utilizador u) throws SemAutorizacaoException, LeilaoInexistenteException{
        if(u.getClass().getName().equals("Comprador")){
            throw new SemAutorizacaoException("Necessita de ser um Vendedor para encerrar um leilão!");
        }
        this.leiloesLock.lock();
        try{
            if(!(leiloes.containsKey(idLeilao))){
                throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");
            }
            else if(!(leiloes.get(idLeilao).getIniciador().equals(u.getUsername()))){
                throw new SemAutorizacaoException("Necessita de ser o iniciador de um leilão para o poder encerrar!");
            }
            else{
                Leilao l = this.leiloes.remove(idLeilao);

                double lic = l.getLicAtual();
                String venc = l.getVencedor();

                for(Comprador c : l.getLicitadores()){
                    String user = c.getUsername();
                    this.mensagens.get(user).setMsg("O leilao "+idLeilao+" foi encerrado com o valor de "+lic+"€, ganho por "+venc+"!",null);
                }

                if(venc != null)    
                    return("O leilao "+idLeilao+" foi encerrado com o valor de "+lic+"€, ganho por "+venc+"!");
                else return("Nenhum Vencedor!");
            }
        }
        finally{
            this.leiloesLock.unlock();
        }
    }

    public ArrayList<String> consultarLeiloes(Utilizador u){
        StringBuilder string;
        string = new StringBuilder();
        ArrayList<String> lista = new ArrayList<>();
        this.leiloesLock.lock();
        try{
            for(Map.Entry<String,Leilao> entry : leiloes.entrySet()){
                Leilao x = entry.getValue();
                String a = entry.getKey();

                if(x.getIniciador().equals(u.getUsername())){
                    string.append("* "+"ID-Leilão = "+a+" - Descrição: "+x.getDescricao()+" - Ult. Licitação: "+x.getLicAtual()+"_");
                }
                else if(x.getVencedor()!= null && x.getVencedor().equals(u.getUsername())){
                        string.append("+ "+"ID-Leilão = "+a+" - Descrição: "+x.getDescricao()+" - Ult. Licitação: "+x.getLicAtual()+"_");
                    }
                else{
                    string.append("ID-Leilão = "+a+" - Descrição: "+x.getDescricao()+" - Ult. Licitação: "+x.getLicAtual()+"_");
                }
            
            }
        }
        finally{
            this.leiloesLock.unlock();
        }
        lista.add("Consultar");
        lista.add(string.toString());
        return lista;
    }

    public Map<String,Utilizador> getUtilizadores(){
        return this.utilizadores;
    }
}
