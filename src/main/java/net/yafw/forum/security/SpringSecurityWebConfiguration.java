package net.yafw.forum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.yafw.forum.utils.CommonConstants;

@Configuration
@EnableWebSecurity
public class SpringSecurityWebConfiguration implements CommonConstants{

//	@Autowired
//	CustomAuthorizationFilter customAuthorizationFilter;
	@Bean
	public SecurityFilterChain customfilterChain(HttpSecurity http) throws Exception {
		http.
			authorizeHttpRequests(
				auth -> auth.requestMatchers("/api/forum/v1/**").authenticated()
				);
		http.csrf( (csrf) -> csrf.disable());
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return (web) -> web.ignoring().
				requestMatchers(USER_SIGNUP_URL,USER_LOGIN_URL);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
//	@Bean
//	public CustomAuthorizationFilter customAuthorizationFilter() {
//		return new CustomAuthorizationFilter();
//	}
}
