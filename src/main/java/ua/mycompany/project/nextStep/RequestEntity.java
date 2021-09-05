package ua.mycompany.project.nextStep;

public class RequestEntity {
	
	private String comand;
	private long id;
	private String dbName;
	
	public RequestEntity(String comand, long id, String dbName) {
		super();
		this.comand = comand;
		this.id = id;
		this.dbName = dbName;
	}
	public String getComand() {
		return comand;
	}
	public void setComand(String comand) {
		this.comand = comand;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}
