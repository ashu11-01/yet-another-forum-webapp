package net.yafw.forum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;	
import org.springframework.stereotype.Component;


import jakarta.validation.Valid;
import net.yafw.forum.dataStore.PostJPARepository;
import net.yafw.forum.dataStore.UserJPARepository;
import net.yafw.forum.exception.ExistingResourceException;
import net.yafw.forum.exception.UserNotFoundException;
import net.yafw.forum.model.Post;
import net.yafw.forum.model.User;
import utils.ErrorMessageConstants;	

@Component
public class UserService implements IBasicService{

	private UserJPARepository userDataStore;
	private PostJPARepository postDataStore;
//	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
	private MessageSource messageSouce;
	@Autowired
	public UserService(UserJPARepository userDataStore, PostJPARepository postDataStore,
			MessageSource messageSouce) {
		this.userDataStore = userDataStore;
		this.postDataStore = postDataStore;
		this.messageSouce = messageSouce;
	}
	
	public List<User> retrieveUsers(){
		List<User> userList = new ArrayList<>();
		userList = userDataStore.findAll();
		return userList;
	}

	public User findOne(UUID id) throws UserNotFoundException {
		Optional<User> result = userDataStore.findById(id);
		if(result.isEmpty()) {
			Object[] params = {id};
			throw new UserNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		return result.get();
	}

	public User createNewUser(@Valid User user) {
		if(null != user.getUserName() && !user.getUserName().isBlank()) {
			user.setUserName(user.getUserName().toLowerCase());
		}
		return userDataStore.save(user);
	}

	public void removeUser(UUID id) throws UserNotFoundException {
		userDataStore.deleteById(id);
	}

	public User updateUser(User user,UUID id) throws UserNotFoundException {
		Optional<User> userToUpdate = userDataStore.findById(id);
		if(userToUpdate.isEmpty()) {
			Object[] params = {id};
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
		if(optionalUser.isEmpty()) {
			Object[] params = {userId};
			throw new UserNotFoundException(getLocalizedErrorMessage(ErrorMessageConstants.USER_NOT_FOUND, params));
		}
		return optionalUser.get().getUserPosts();
	}

	public Post createNewPostForUser(UUID userId, @Valid Post post) throws UserNotFoundException {
		Optional<User> optionalUser = userDataStore.findById(userId);
		if(optionalUser.isEmpty()) {
			Object[] params = {userId};
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
		String errorMesssageFromResouce = messageSouce.getMessage(errorMessageCode, params , "Default Message", locale );
		return errorMesssageFromResouce;
	}

	public User registerUser(@Valid User user) throws ExistingResourceException {
		User existingUser = null;
		String inputEmailAddress = user.getUserEmailAddress();
		String inputUserName = user.getUserName().toLowerCase();
		if(null != inputEmailAddress) {
			existingUser = userDataStore.findByUserNameOrUserEmailAddress(inputUserName, inputEmailAddress);
			if(null != existingUser) {
				boolean isEmailAddressExists = existingUser.getUserEmailAddress().equals(inputEmailAddress);
				boolean isUserNameExists = existingUser.getUserName().equals(inputUserName);
				
				Object[] params = null;
				StringBuilder errorMesage = new StringBuilder(getLocalizedErrorMessage(ErrorMessageConstants.EXISTING_USER, params));
				if(isEmailAddressExists) {
					params = new Object[1];
					params[0] = user.getUserEmailAddress();
					errorMesage.append(getLocalizedErrorMessage(ErrorMessageConstants.EMAIL_ID_DUPLICATE, params));
				}
				else if(isUserNameExists) {
					params = new Object[1];
					params[0] = user.getUserEmailAddress();
					errorMesage.append(getLocalizedErrorMessage(ErrorMessageConstants.USERNAME_DUPLICATE, params));
				}
				throw new ExistingResourceException(errorMesage.toString());
			}
		}
		/* START: Tactical solution for password storage. 
		 * TODO : Spring Security to be used
		 */
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		/* END: Tactical solution for password storage.
		 *
		 */
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return createNewUser(user);
	}

	public User authenticate(@Valid User user) {
		User existingUser = null;
		if(null != user.getUserName() && !user.getUserName().isBlank()) {			
			existingUser = userDataStore.findByUserName(user.getUserName());
		}
		if(existingUser != null) {
				/* START: Tactical solution for encoding password. 
				 * TODO : Spring Security to be used
				 */
			if(BCrypt.checkpw(user.getPassword(), existingUser.getPassword())){
				/* END : Tactical solution for encoding password.
				 * 
				 */
//			if(bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
				return existingUser;
			}
			else {
				existingUser = null;
			}
		}
		return existingUser;
	}
}
