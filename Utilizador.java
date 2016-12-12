public abstract class Utilizador{

	private String username;
	private String password;

	public Utilizador(){
		this.username = "n/a";
		this.password = "n/a";
	}

	public Utilizador(Utilizador c) {
        this.username = c.getUsername();
        this.password = c.getPassword();
    }

    public Utilizador(String username, String password){
    	this.username = username;
    	this.password = password;
    }

    public String getUsername(){
    	return this.username;
    }

    public String getPassword(){
    	return this.password;
    }

    public void setUsername(String username){
    	this.username = username;
    }

    public void setPassword(String password){
    	this.password = password;
    }

    public String toString(){
    	StringBuilder string;
    	string = new StringBuilder();
    	string.append("Nome: ");
    	string.append(this.nome+'\n');
    	string.append("Password: ");
    	string.append(this.password+'\n');
    	string.append("Username: ");
    	string.append(this.username+'\n');
        return string.toString();
    }

    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Utilizador u = (Utilizador) obj;
        return u.getNome().equals(this.nome) && u.getUsername().equals(this.username)
            && u.getPassword().equals(this.password);

    }

    public abstract Utilizador clone();


}