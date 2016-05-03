package swing;

public class PresEvent {
	private String user;
	private String password;
	private int port;
	
	public PresEvent(String user, String password, int port) {
		super();
		this.user = user;
		this.password = password;
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	} 
	
	
}
