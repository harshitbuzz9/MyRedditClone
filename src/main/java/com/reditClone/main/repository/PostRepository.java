package com.reditClone.main.repository;

import com.reditClone.main.models.Post;
import com.reditClone.main.models.Subreddit;
import com.reditClone.main.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
}