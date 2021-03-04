package ci.parkerbase.models;

import java.util.List;

public class Reponse<T> {
	private int status;
	
	private List<String> messages;
	private T body;
	public Reponse() {
		super();
		
	}
	public Reponse(int status, List<String> messages, T body) {
		super();
		this.status = status;
		this.messages = messages;
		this.body = body;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}

	
}
