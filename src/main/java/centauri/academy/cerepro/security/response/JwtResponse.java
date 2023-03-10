package centauri.academy.cerepro.security.response;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String username;
	private String roles;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
	
	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JwtResponse(String token, String type, String username, String roles) {
		super();
		this.token = token;
		this.type = type;
		this.username = username;
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "JwtResponse [token=" + token + ", type=" + type + ", username=" + username + ", roles=" + roles + "]";
	}
	
	

}
