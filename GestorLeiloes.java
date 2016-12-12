import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;


public class GestorLeiloes {
    private Map<String,Leilao> leiloes;
    private Map<String,Utilizador> utilizadores;
    private int idLeilao=1;
    private Condition c;
    
    
    public GestorLeiloes(){
        this.leiloes = new HashMap<>();
        this.utilizadores = new HashMap<>();
    }
    
    public synchronized Utilizador iniciarSessao(String username, String password) throws UsernameInexistenteException, PasswordIncorretaException{
        if(this.utilizadores.containsKey(username)){
            throw new UsernameInexistenteException("Username inexistente!");
        }
        else{
            if(this.utilizadores.get(username).getPassword().equals(password)){
                throw new PasswordIncorretaException("A password está incorreta!");
            }
            else{
                return this.utilizadores.get(username);
            }
        }
    }
    
    public synchronized void registarUtilizador(String user, String pass, int op) throws UsernameInvalidoException{
        if(this.utilizadores.containsKey(user)){
                        throw new UsernameInvalidoException("Username já se encontra em uso!");
        }
        else {
            switch(op){
                case 0: Comprador c = new Comprador (user,pass,null);
                        this.utilizadores.put(user,c);
                        System.out.println("cheguei");
                        break;
                case 1: Vendedor v = new Vendedor (user,pass,null);
                        this.utilizadores.put(user,v);
                        break;
            }
        }
    }
    
    public synchronized String adicionarLeilao(Leilao l,Utilizador u) throws SemAutorizacaoException{
        if(u.getClass().getName().equals("Comprador")){
            throw new SemAutorizacaoException("Necessita de ser um Vendedor para iniciar um Leilão!");
        }
        else { 
               Leilao lei = l.clone();
               String id = Integer.toString(idLeilao);
               leiloes.put(id,l);
               idLeilao++;
               //((Vendedor)u).addLeilao(l);
        }
        return Integer.toString(idLeilao-1);
    }
    
    public synchronized void licitarLeilao(String idLeilao,Utilizador u,double lic) throws SemAutorizacaoException, LeilaoInexistenteException, LicitacaoInvalidaException{
        if(u.getClass().getName().equals("Vendedor")){
            throw new SemAutorizacaoException("Necessita de ser um Comprador para fazer uma licitação!");
        }
        else if(!(leiloes.containsKey(idLeilao))){
            throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");
        }
        else if(leiloes.get(idLeilao).getLicAtual() > lic){
            throw new LicitacaoInvalidaException("Necessita de ser um Vendedor para iniciar um Leilão!");
        }
        else{

            Leilao l = leiloes.get(idLeilao);
            /*ReentrantLock rl = l.getLock();
            this.c = l.getCondition();
            rl.lock();
            
            try{*/
                l.licitar((Comprador)u, lic);

           /*     c.await();
                
            }
            catch (InterruptedException e){
                System.out.println("erro");
            }
            finally{
                rl.unlock();
            }*/

        }
    }
    
    public synchronized String encerrarLeilao(String idLeilao,Utilizador u) throws SemAutorizacaoException, LeilaoInexistenteException{
        if(u.getClass().getName().equals("Comprador")){
            throw new SemAutorizacaoException("Necessita de ser um Vendedor para encerrar um leilão!");
        }
        else if(!(leiloes.containsKey(idLeilao))){
            throw new LeilaoInexistenteException("Não existe nenhum leilão com tal ID!");
        }
        else if(!(leiloes.get(idLeilao).getIniciador().equals(u))){
            throw new SemAutorizacaoException("Necessita de ser o iniciador de um leilão para o poder encerrar!");
        }
        else{
            double lic = leiloes.get(idLeilao).getLicAtual();
            String venc = leiloes.get(idLeilao).getVencedor();

            return("O leilao "+idLeilao+" foi encerrado com o valor de "+lic+"€, ganho por "+venc+"!");}
    }

    public synchronized String[] consultarLeiloes(Utilizador u){
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

    public Map<String,Utilizador> getUtilizadores (){
        return this.utilizadores;
    }
}
