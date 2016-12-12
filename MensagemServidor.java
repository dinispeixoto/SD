import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class MensagemServidor {
	private String mensagem;
	private Condition c;
	private ReentrantLock lock;

	public MensagemServidor(Condition c, ReentrantLock lock){
		this.mensagem=null;
		this.c = c;
		this.lock = lock;
	}

	public void setMsg(String msg){
		this.lock.lock();
		try{
			this.mensagem = msg;
			c.signal();
		}
		finally{
			this.lock.unlock();
		}
	}

	public String getMsg(){
		return this.mensagem;
	}
}