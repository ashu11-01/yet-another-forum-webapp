package net.yafw.forum.model;

public class LoginResponse {

	private String username;
	
	private User user;
	
	private String token;

	
	/**
	 * @param username
	 * @param user
	 * @param token
	 */
	public LoginResponse(String username, User user, String token) {
		super();
		this.username = username;
		this.user = user;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
