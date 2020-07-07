package spring.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.blog.model.User;
import spring.blog.repository.UserRepository;

@Service
@Primary
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public boolean authenticate(String username, String password) {
		return false;
	}
	@Override
	public Page<User> findAll(Pageable pageable) {
		return this.userRepo.findAll(pageable);
	}
	@Override
	public User findById(Long id) {
		return this.userRepo.getOne(id);
	}
	@Override
	public User create(User user) {
		// Encode user's password before adding it to database
		user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));
		return this.userRepo.save(user);
	}
	@Override
	public User edit(User user) {
		return this.userRepo.save(user);
	}
	@Override
	public void deleteById(Long id) {
		this.userRepo.deleteById(id);
	}
	@Override
	public User findByUsername(String username) {
		return this.userRepo.findByUsername(username);
	}	
}
