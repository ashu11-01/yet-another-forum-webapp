package net.yafw.forum.model;

import lombok.Getter;

@Getter
public class LoginRequest {

	private String username;
	
	private String password;
	
	
	
	/**
	 * @param username username of the User to login
	 * @param password password of the User to login
	 */
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

    public void setUsername(String username) {
		this.username = username;
	}

    public void setPassword(String password) {
		this.password = password;
	}
}
