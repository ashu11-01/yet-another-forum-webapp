package net.yafw.forum.model;

import lombok.Getter;
import lombok.Setter;

@Setter
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

}
