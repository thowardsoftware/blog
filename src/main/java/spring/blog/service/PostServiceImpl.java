package spring.blog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.blog.model.Post;
import spring.blog.repository.PostRepository;

// Implement the business logic to Use the DB
@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepo;

	@Override
	public List<Post> findAll() { return this.postRepo.findAll(); }
	@Override
	public Page<Post> findAll(Pageable pageable){ 
		return this.postRepo.findAll(pageable); 
	}
	@Override
	public List<Post> findLatest3() {
		// Query the DB
		return this.postRepo.findLatest3Posts( PageRequest.of(0,3) );
	}
	@Override
	public Post findById(Long id) {
		return this.postRepo.findById(id).orElse(null);
	}
	@Override
	public Post create(Post post) { 
		return this.postRepo.save(post);
	}
	@Override
	public Post edit(Post post) { 
		return this.postRepo.save(post); 
	}
	@Override
	public void deleteById(Long id) { 
		this.postRepo.deleteById(id);
	}
}
