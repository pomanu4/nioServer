package ua.mycompany.project.nextStep;

import org.joda.time.DateTime;

public class ClientInfo {
	
	private long id;
	private String message;
	
	public ClientInfo() {
		super();
		this.id = DateTime.now().getMillis();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ClientInfo [id= " + id + ", message=" + message + "]";
	}
}
