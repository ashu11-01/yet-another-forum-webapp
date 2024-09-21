package net.yafw.forum.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

	private String username;
	
	private User user;
	
	private String token;

	
	/**
	 * @param username username of the logged in user
	 * @param user authenticated User
	 * @param token generated JWT
	 */
	public LoginResponse(String username, User user, String token) {
		super();
		this.username = username;
		this.user = user;
		this.token = token;
	}


}
