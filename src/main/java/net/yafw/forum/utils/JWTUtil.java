package net.yafw.forum.utils;

import java.util.Date;

import net.yafw.forum.service.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import net.yafw.forum.model.User;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

	@Autowired
	AppConfig appConfig;

    /**
	 * 
	 * @param existingUser the authenticated user
	 * @return the Json Web Token (JWT)
	 */
	public String getToken(User existingUser) {
		Algorithm algorithm = Algorithm.HMAC256(appConfig.getJwtSecret());
		long tokenValidity = System.currentTimeMillis() + appConfig.getTokenValidityInMinutes()
				* 60L * 1000;
		Builder builder =  JWT.
			create().
			withSubject(existingUser.getUserName()).
			withIssuer("yafw").
			withExpiresAt(new Date(tokenValidity));
		if(null == builder) {
			throw new IllegalStateException();
		}
		return builder.sign(algorithm);
	}
	
	public boolean verifyToken(String accessToken,String username) {
		Algorithm algorithm = Algorithm.HMAC256(appConfig.getJwtSecret());
		DecodedJWT decodedJWT;
		decodedJWT = JWT.require(algorithm).build().verify(accessToken);
		return decodedJWT.getSubject().equals(username);
	}

}
