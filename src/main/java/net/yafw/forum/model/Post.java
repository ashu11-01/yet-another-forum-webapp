package net.yafw.forum.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "post_details")
public class Post{
	
	@Column(name="id",unique = true)
	@GeneratedValue(strategy = GenerationType.UUID)
	@Id
	private UUID id;
	
	@Size(min = 2)
	private String title;
	
	private String description;
	
	private List<String> tags;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User author;
	
	private int upVotes;
	
	private int downVotes;

	
	/**
	 * Default constructor for Hibernate serialization
	 */
	protected Post() {
		super();
	}

	public Post(UUID id, @Size(min = 2) String title, String description, List<String> tags, User author, int upVotes,
			int downVotes) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.author = author;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
	}


}
