package net.yafw.forum.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import net.yafw.forum.model.User;

public class JWTUtil {

	@Value("${security.jwt.secret}")
	private static String jwtSecret;
	@Value("{security.jwt.validiy}")
	private static int tokenValidityInMinutes;
	/**
	 * 
	 * @param existingUser
	 * @return
	 */
	public static String getToken(User existingUser) {
		jwtSecret = System.getenv("security.jwt.secret");
		tokenValidityInMinutes = Integer.parseInt(System.getenv("security.jwt.validiy"));
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		long tokenValidity = System.currentTimeMillis() + tokenValidityInMinutes * 60 * 1000;
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
	
	public static boolean verifyToken(String accessToken,String username) {
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		DecodedJWT decodedJWT = null;
		decodedJWT = JWT.require(algorithm).build().verify(accessToken);
		return decodedJWT.getSubject().equals(username);
	}

}
