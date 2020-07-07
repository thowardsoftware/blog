package spring.blog.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 300)
	private String title;
	
	@Lob 
	@Column(nullable = false)
	private String body;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User author;
	
	@Column(nullable = false)
	private Date date = new Date();

	// Default Constructor
	public Post() {
	}

	// Parameterized Constructor
	public Post(Long id, String title, String body, User author) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.author = author;
	}

	// Getters and Setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String body) { this.body = body; }

	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	// toString Method
	@Override
	public String toString() {
		return "Post []";
	}

}
