import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.util.List;
import java.util.ArrayList;

public class MensagemServidor {
	private List<String> mensagem;
	private Condition c;
	private ReentrantLock lock;
	private int index;

	public MensagemServidor(Condition c, ReentrantLock lock){
		this.mensagem = new ArrayList<>();
		this.c = c;
		this.lock = lock;
		this.index = 0;
	}

	public void setMsg(String msg){
		this.lock.lock();
		try{
			this.mensagem.add(msg);
			//this.index++;
			c.signal();
		}
		finally{
			this.lock.unlock();
		}
	}

	public String getMsg(){
		return this.mensagem.get((index++));
	}

	public Condition getCondition(){
		return this.c;
	}

	public ReentrantLock getLock(){
		return this.lock;
	}
}