package net.yafw.forum.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import net.yafw.forum.dataStore.PostJPARepository;
import net.yafw.forum.dataStore.UserJPARepository;
import net.yafw.forum.exception.ExistingResourceException;
import net.yafw.forum.exception.UserNotFoundException;
import net.yafw.forum.model.LoginRequest;
import net.yafw.forum.model.LoginResponse;
import net.yafw.forum.model.Post;
import net.yafw.forum.model.User;
import net.yafw.forum.utils.ErrorMessageConstants;
import net.yafw.forum.utils.JWTUtil;


@Component
public class UserService implements IBasicService, UserDetailsService {

	@Autowired
	private UserJPARepository userDataStore;
	@Autowired
	private  PostJPARepository postDataStore;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	JWTUtil jwtUtil;

	public List<User> retrieveUsers() {
		List<User> userList;
		userList = userDataStore.findAll();
		return userList;
	}

	public User findOne(UUID id) throws UserNotFoundException {
		Optional<User> result = userDataStore.findById(id);
		if (result.isEmpty()) {
			Object[] params = { id };
			throw new UserNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		return result.get();
	}

	public User createNewUser(@Valid User user) {
		if (null != user.getUserName() && !user.getUserName().isBlank()) {
			user.setUserName(user.getUserName().toLowerCase());
		}
		return userDataStore.save(user);
	}

	public void removeUser(UUID id) throws UserNotFoundException {
		userDataStore.deleteById(id);
	}

	public User updateUser(User user, UUID id) throws UserNotFoundException {
		Optional<User> userToUpdate = userDataStore.findById(id);
		if (userToUpdate.isEmpty()) {
			Object[] params = { id };
			throw new UserNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		User userEntity = userToUpdate.get();
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setReputation(user.getReputation());
		userEntity.setUserName(user.getUserName());
		userEntity.setUserEmailAddress(user.getUserEmailAddress());
		return userDataStore.save(userEntity);
	}

	public List<Post> getPostsByUser(UUID userId) throws UserNotFoundException {
		Optional<User> optionalUser = userDataStore.findById(userId);
		if (optionalUser.isEmpty()) {
			Object[] params = { userId };
			throw new UserNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		return optionalUser.get().getUserPosts();
	}

	public Post createNewPostForUser(UUID userId, @Valid Post post) throws UserNotFoundException {
		Optional<User> optionalUser = userDataStore.findById(userId);
		if (optionalUser.isEmpty()) {
			Object[] params = { userId };
			throw new UserNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		User postAuthor = optionalUser.get();
		post.setAuthor(postAuthor);
		return postDataStore.save(post);
	}

	/**
	 * @param errorMessageCode TODO
	 * @param params
	 * @return
	 */
	private String getLocalizedErrorMessage(String errorMessageCode, Object[] params) {
		Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(errorMessageCode, params, "Default Message", locale);
	}

	public User registerUser(@Valid User user) throws ExistingResourceException {
		User existingUser;
		String inputEmailAddress = user.getUserEmailAddress();
		String inputUserName = user.getUserName().toLowerCase();
		if (null != inputEmailAddress) {
			existingUser = userDataStore.findByUserNameOrUserEmailAddress(inputUserName, inputEmailAddress);
			if (null != existingUser) {
				boolean isEmailAddressExists = existingUser.getUserEmailAddress().equals(inputEmailAddress);
				boolean isUserNameExists = existingUser.getUserName().equals(inputUserName);

				Object[] params = null;
				StringBuilder errorMessage = new StringBuilder(
						getLocalizedErrorMessage(ErrorMessageConstants.EXISTING_USER, params));
				if (isEmailAddressExists) {
					params = new Object[1];
					params[0] = user.getUserEmailAddress();
					errorMessage.append(getLocalizedErrorMessage(ErrorMessageConstants.EMAIL_ID_DUPLICATE, params));
				} else if (isUserNameExists) {
					params = new Object[1];
					params[0] = user.getUserEmailAddress();
					errorMessage.append(getLocalizedErrorMessage(ErrorMessageConstants.USERNAME_DUPLICATE, params));
				}
				throw new ExistingResourceException(errorMessage.toString());
			}
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return createNewUser(user);
	}

	/**
	 *
	 * @param loginRequest the login request
	 * @return the login response
	 */
	public LoginResponse authenticate(@Valid LoginRequest loginRequest) { 
		LoginResponse authenticatedUser = null;
		User existingUser = null;
		String requestUsername = loginRequest.getUsername();
		if(null != requestUsername && !requestUsername.isBlank()) {
			existingUser = userDataStore.findByUserName(requestUsername); 
		}
		if(existingUser != null) {
			if(passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
				String token = (jwtUtil.getToken(existingUser));
				authenticatedUser = new LoginResponse(existingUser.getUserName(),
						existingUser, token);
			}
		}
		return authenticatedUser; 
	}
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User optioanlUser = userDataStore.findByUserName(username);
		if (null == optioanlUser) {
			Object[] params = new Object[1];
			params[0] = username;
			throw new UsernameNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(
                optioanlUser.getUserName().toLowerCase(), optioanlUser.getPassword(), authorities);
	}
	
	public User findUserByUsername(String username) {
		return userDataStore.findByUserName(username);
	}
}
