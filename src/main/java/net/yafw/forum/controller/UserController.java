package net.yafw.forum.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.yafw.forum.exception.ExistingResourceException;
import net.yafw.forum.exception.UserNotFoundException;
import net.yafw.forum.model.Post;
import net.yafw.forum.model.User;
import net.yafw.forum.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/forum/v1")
public class UserController {

	private UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(path="/users")
	public List<User> getAllUsers(){
		return userService.retrieveUsers();
	}
	
	@GetMapping(path="/users/{id}")
	
	public EntityModel<User> getUser(@PathVariable UUID id) throws UserNotFoundException {
		User user = null;
		try {
			user = userService.findOne(id);
		} catch (UserNotFoundException e) {
			throw e;
		}
		EntityModel<User> userEntity = EntityModel.of(user);
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
		userEntity.add(link.withRel("all-users"));
		return userEntity;
	}
	
	@PostMapping(path="/users",consumes = MediaType.APPLICATION_JSON_VALUE)
	public User addUser(@Valid @RequestBody User user) {
		return userService.createNewUser(user);
	}
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable UUID id) throws UserNotFoundException {
		try {
			userService.removeUser(id);
		} catch (UserNotFoundException e) {
			throw e;
		}
	}
	
	@PutMapping(path="/users/{id}")
	public User updateUser(@RequestBody User user, @PathVariable UUID id) throws UserNotFoundException {
		return userService.updateUser(user,id);
	}
	
	@PostMapping(path="/users/{id}/posts",consumes = MediaType.APPLICATION_JSON_VALUE)
	public Post addUser(@PathVariable UUID id, @Valid @RequestBody Post post) throws UserNotFoundException {
		return userService.createNewPostForUser(id,post);
	}
	
	@GetMapping(path="/users/{id}/posts")
	public List<Post> userPosts(@PathVariable UUID id) throws UserNotFoundException{
		return userService.getPostsByUser(id);
	}
	
	@PostMapping(path="/users/register")
	public User registerUser(@Valid @RequestBody User user) throws ExistingResourceException {
		return userService.registerUser(user);
	}
	
	@PostMapping(path="/users/login")
	public User login(@RequestBody User user) throws ExistingResourceException {
		return userService.authenticate(user);
	}
}
