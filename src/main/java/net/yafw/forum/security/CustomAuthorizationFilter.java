package net.yafw.forum.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.yafw.forum.utils.CommonConstants;
import net.yafw.forum.utils.JWTUtil;
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestURL = request.getRequestURI();
		if (CommonConstants.USER_LOGIN_URL.equals(requestURL) || CommonConstants.USER_SIGNUP_URL.equals(requestURL)) {
			doFilter(request, response, filterChain);
		}
		else {
			String username = request.getHeader("X-username");
			String accessToken = request.getHeader("Authorization").substring("Bearer ".length());
			boolean isTokenValid = jwtUtil.verifyToken(accessToken, username);
			if(isTokenValid) {
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("USER"));
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
				SecurityContextHolder.getContext().setAuthentication(authToken);
				filterChain.doFilter(request, response);
			}
			else {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType(MediaType.TEXT_PLAIN_VALUE);
				response.getWriter().print("User is not authorized to access this resource");
			}
		}
	}
}
