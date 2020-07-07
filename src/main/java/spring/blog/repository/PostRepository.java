package spring.blog.repository;

import java.util.List;
import spring.blog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	// Query the DB
	@Query(value="SELECT p.* FROM posts p ORDER BY p.date DESC", nativeQuery = true)
	List<Post> findLatest3Posts(Pageable pageable);

}