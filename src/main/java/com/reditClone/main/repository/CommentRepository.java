package com.reditClone.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.reditClone.main.models.Comment;
import com.reditClone.main.models.Post;
import com.reditClone.main.models.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPost(Post post);
	List<Comment> findAllByUser(User user);
}
