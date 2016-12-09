import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;


public class Comprador extends Utilizador {

    private ArrayList<String> leiloes;

    
    public Comprador(){
        super("","","");
        this.leiloes = new ArrayList<String>();
    }

    
    public Comprador(Comprador v){
        super(v);
        this.leiloes = v.getLeiloes();
    }


    public Comprador(String username, String password, String nome, ArrayList<String> leiloes){
        super(username,password,nome);
        this.leiloes = new ArrayList<String>();
        if(leiloes!= null) this.setLeiloes(leiloes);
    }

    
    public ArrayList<String> getLeiloes(){}
       
    public void setLeiloes(ArrayList<String> leiloes){}

    public Comprador clone(){
        return new Comprador(this);
    }

   public boolean equals(Object obj){
      if(this == obj)
        return true;
      if ((obj==null) || (this.getClass() != obj.getClass()))
        return false;
      Comprador v = (Comprador) obj;
        return (super.equals(v)); /* falta equals de cada um dos imoveis caso seja necessário */
   }

}