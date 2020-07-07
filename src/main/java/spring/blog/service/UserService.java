package spring.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.blog.model.User;

public interface UserService {
	boolean authenticate(String username, String password);
	Page<User> findAll(Pageable pageable);
	User findByUsername(String username);
	User findById(Long id); 
	User create(User user);
	User edit(User user);
	void deleteById(Long id);
}
