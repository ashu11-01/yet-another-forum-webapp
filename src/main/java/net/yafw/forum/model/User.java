package net.yafw.forum.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "user_details")
public class User{

	@Column(name="id",unique = true)
	@GeneratedValue(strategy = GenerationType.UUID)
	@Id
	private UUID id;
	
	@NotBlank
	@Column(name = "first_name",nullable = false)
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private int reputation;
	
	
	@NotBlank
	@Column(name="user_name",unique = true, nullable = false)
	private String userName;
	
	@Email
	@Column(name = "email",unique = true)
	private String userEmailAddress;
	
	@JsonIgnore
	@OneToMany(mappedBy = "author")
	private List<Post> userPosts;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;	
	
	/**
	 * Empty Constructor for Hibernate serialization
	 */
	protected User() {
		super();
	}
	
	

	public User(UUID id, @NotBlank String firstName, String lastName, int reputation, @NotBlank String userName,
			@Email String userEmailAddress, List<Post> userPosts, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reputation = reputation;
		this.userName = userName;
		this.userEmailAddress = userEmailAddress;
		this.userPosts = userPosts;
		this.password = password;
	}


}
