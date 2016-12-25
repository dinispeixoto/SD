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
    /*Lock para prevenir exclusao mutua*/
    private ReentrantLock lock;
    
    
    public GestorLeiloes(){
        this.leiloes = new HashMap<>();
        this.utilizadores = new HashMap<>();
        this.mensagens = new HashMap<>();
        this.lock = new ReentrantLock();
    }
    
    public Utilizador iniciarSessao(String username, String password) throws UsernameInexistenteException, PasswordIncorretaException{
        if(!this.utilizadores.containsKey(username)){
            throw new UsernameInexistenteException("Username inexistente!");
        }
        else{
            if(!this.utilizadores.get(username).getPassword().equals(password)){
                throw new PasswordIncorretaException("A password está incorreta!");
            }
            else{
                return this.utilizadores.get(username);
            }
        }
    }
    
    public void registarUtilizador(String user, String pass, int op, MensagemServidor ms) throws UsernameInvalidoException{
        this.lock.lock();
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
            this.lock.unlock();
        }
    }
    
    public String adicionarLeilao(Leilao l,Utilizador u) throws SemAutorizacaoException{
        this.lock.lock();
        try{
            if(u.getClass().getName().equals("Comprador")){
                throw new SemAutorizacaoException("Necessita de ser um Vendedor para iniciar um Leilão!");
            }
            else { 
               Leilao lei = l.clone();
               String id = Integer.toString(this.idLeilao);
               leiloes.put(id,l);
               this.idLeilao++;
            }
            return Integer.toString(idLeilao-1);
        }
        finally{
            this.lock.unlock();
        }
    }
    
    public void licitarLeilao(String idLeilao,Utilizador u,double lic) throws SemAutorizacaoException, LeilaoInexistenteException, LicitacaoInvalidaException{
        if(u.getClass().getName().equals("Vendedor")){
            throw new SemAutorizacaoException("Necessita de ser um Comprador para fazer uma licitação!");
        }
        else if(!(leiloes.containsKey(idLeilao))){
                throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");
        }
        else if(leiloes.get(idLeilao).getLicAtual() > lic){
                throw new LicitacaoInvalidaException("Licitação com valor inferior a última licitação!");
        }
        else if(leiloes.get(idLeilao).getLicAtual() == lic){
                throw new LicitacaoInvalidaException("Licitação com valor igual a última licitação!");
        }
        else{
            this.lock.lock();
            try{
                Leilao l = this.leiloes.get(idLeilao);
        
                String topo = l.getVencedor();
            
                l.licitar((Comprador)u, lic);
            
                if(topo != null)
                    this.mensagens.get(topo).setMsg("No leilao "+idLeilao+" foi ultrapassado pelo "+u.getUsername()+", com o valor de "+lic+"€",null);
            }
            finally{
                this.lock.unlock();
            }
        }  
    }
    
    public String encerrarLeilao(String idLeilao,Utilizador u) throws SemAutorizacaoException, LeilaoInexistenteException{
        if(u.getClass().getName().equals("Comprador")){
            throw new SemAutorizacaoException("Necessita de ser um Vendedor para encerrar um leilão!");
        }
        else if(!(leiloes.containsKey(idLeilao))){
            throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");
        }
        else if(!(leiloes.get(idLeilao).getIniciador().equals(u.getUsername()))){
            throw new SemAutorizacaoException("Necessita de ser o iniciador de um leilão para o poder encerrar!");
        }
        else{
            this.lock.lock();
            try{
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
            finally{
                this.lock.unlock();
            }
        }
    }

    public ArrayList<String> consultarLeiloes(Utilizador u){
        StringBuilder string;
        string = new StringBuilder();
        ArrayList<String> lista = new ArrayList<>();
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
        lista.add("Consultar");
        lista.add(string.toString());
        return lista;
    }

    public Map<String,Utilizador> getUtilizadores(){
        return this.utilizadores;
    }
}
