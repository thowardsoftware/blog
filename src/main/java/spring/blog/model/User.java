package spring.blog.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="users")
public class User {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30, unique = true)
	@NotEmpty(message = "Please provide your Username")
	private String username;
	
	@Column(length = 60)
	@Length(min = 5, message = "Your password must have at least 5 characters")
	@NotEmpty(message = "Please provide your password")
	private String passwordHash;
	
	@Column(length = 100)
	@NotEmpty(message = "Please provide your full name")
	private String fullName;
	
	@OneToMany(mappedBy = "author")
	private Set<Post> posts = new HashSet<>();

	// Default Constructor
	public User() {
	}

	// Parameterized Constructor
	public User(Long id, String username, String fullName) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	// toString Method
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", fullName=" + fullName + "]";
	}

}
