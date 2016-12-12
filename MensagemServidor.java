import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class MensagemServidor {
	private String mensagem;
	private Condition c;

	public MensagemServidor(Condition c){
		this.mensagem=null;
	}

	public void setMsg(String msg){
		this.mensagem = msg;
		c.signal();
	}

	public String getMsg(){
		return this.mensagem;
	}
}