package spring.blog.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.blog.model.Post;

// Interface for business logic
public interface PostService {
	List<Post> findAll();
	Page<Post> findAll(Pageable pageable);
	List<Post> findLatest3();
	Post findById(Long id);
	Post create(Post post);
	Post edit(Post post);
	void deleteById(Long id);
}
